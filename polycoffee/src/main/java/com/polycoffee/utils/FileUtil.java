package com.polycoffee.utils;

import jakarta.servlet.http.Part;
import java.io.File;

public class FileUtil {
    public static String saveFile(Part part, String uploadDir) {
        try {
            String filename = part.getSubmittedFileName();
            if (filename == null || filename.isEmpty())
                return null;
            File dir = new File(uploadDir);
            if (!dir.exists())
                dir.mkdirs();
            String filePart = uploadDir + File.separator + filename;
            part.write(filePart);
            return filename;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }
}
