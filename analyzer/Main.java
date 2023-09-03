package analyzer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        if (args.length == 3) {
            String filePath = args[0];
            String filePattern = args[1];
            String resultingType = args[2];

            try (InputStream inputStream = new FileInputStream(filePath)) {
                byte[] patternByteArray = filePattern.getBytes();
                byte[] fileByteArray = inputStream.readAllBytes();

                if (isSubArray(patternByteArray, fileByteArray)) {
                    System.out.println(resultingType);
                } else {
                    System.out.println("Unknown file type");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static boolean isSubArray(byte[] subArray, byte[] array) {
        if (subArray == null || array == null) {
            return false;
        }
        if (subArray.length > array.length) {
            return false;
        }
        if (array.length == 0) {
            return true;
        }
        for (int i = 0; i < array.length - subArray.length; i++) {
            if (array[i] == subArray[0]) {
                boolean isMatch = true;
                for (byte b : subArray) {
                    if (b != array[i]) {
                        isMatch = false;
                        break;
                    }
                    i++;
                }
                if (isMatch) {
                    return true;
                }
            }
        }
        return false;
    }
}
