package com.github.addshore.facebook.data.image.exif;

import com.thebuzzmedia.exiftool.ExifTool;
import com.thebuzzmedia.exiftool.ExifToolBuilder;
import org.apache.commons.io.FilenameUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.cli.*;

public class Main {

    public static void main(String[] args) {
        launch(args);
    }

    private static void launch(String[] args) {
        Options options = new Options();
        Option fbPath = new Option("i", true, "FB directory");
        fbPath.setRequired(true);
        options.addOption(fbPath);
        Option exifPath = new Option("e", true, "Path to exif");
        options.addOption(exifPath);
        Option target = new Option("t", true, "Target Album directory");
        target.setRequired(true);
        options.addOption(target);
        try {
            CommandLine cmd = new DefaultParser().parse(options, args);
            process(cmd);

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            new HelpFormatter().printHelp("facebook-data-image-exif", options);

            System.exit(1);
        }

    }

    private static void process(CommandLine cmd) {

        String fbPath = cmd.getOptionValue("i");
        String targetDir=cmd.getOptionValue("t");
        File targetAlbumDir=new File(targetDir); 
        targetAlbumDir.mkdirs();
        List<String> lines = new ArrayList<>();
        File dirFile = new File(fbPath);
        ExifToolBuilder builder = new ExifToolBuilder();

        // // If we have more than one processor, use a pool strategy of that size
        // if (Runtime.getRuntime().availableProcessors() > 1) {
        // builder.withPoolSize(Runtime.getRuntime().availableProcessors());
        // }

        // builder.enableStayOpen();
        ExifTool exifTool = builder.build();

        String initialStateMessage = "foo";
        ProcessingTask task = new ProcessingTask(
                lines,
                dirFile,
                exifTool,
                initialStateMessage,
                targetAlbumDir,
                new MainOptions(
                        true,
                        false,
                        false));

        task.call();

    }

    /**
     * Looks for an exiftool executable in the system PATH
     * where an exiftool executable would be any file that without an extension has
     * the string name "exiftool"
     *
     * @return File
     * @throws FileNotFoundException
     */
    private File getExifToolFromPath() throws FileNotFoundException {
        for (String dirString : System.getenv("PATH").split(System.getProperty("path.separator"))) {
            File dir = new File(dirString);
            if (dir.isDirectory()) {
                for (File file : Objects.requireNonNull(dir.listFiles())) {
                    String fileWithoutExt = FilenameUtils.removeExtension(file.getName());
                    if (fileWithoutExt.equals("exiftool")) {
                        return file;
                    }
                }
            }
        }
        throw new FileNotFoundException();
    }

}
