package save;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress gp1 = new GameProgress(100, 2, 1, 500);
        GameProgress gp2 = new GameProgress(80, 3, 5, 1700);
        GameProgress gp3 = new GameProgress(61, 6, 9, 4100);



        //Записываем объекты в файлы
        saveGame("D:\\Games\\savegames\\save1.dat", gp1);
        saveGame("D:\\Games\\savegames\\save2.dat", gp2);
        saveGame("D:\\Games\\savegames\\save3.dat", gp3);



        List<String> dirs = new ArrayList<>(List.of(
                "D:\\Games\\savegames\\save1.dat",
                "D:\\Games\\savegames\\save2.dat",
                "D:\\Games\\savegames\\save3.dat")
        );

        zipFile("D:\\Games\\savegames\\saves.zip", dirs);

        //Удаление файлов, находящихся вне архива с помощью File
        File dir = new File("D:\\Games\\savegames");
        File[]files = dir.listFiles();
        for (int i = 0; i < files.length-1; i++) {
            if (files[i].delete()) {
                System.out.println("file deleted");
            } else {
                System.out.println("Something wrong");
            }

        }


    }

    //Метод для сохранения игры
    public static void saveGame(String dir, GameProgress gameProgress) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dir));) {
            oos.writeObject(gameProgress);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Метод для архивации файлов сохранений
    public static void zipFile(String dir, List<String> fileDirList) {
        int num = 0;
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(dir));) {
            for (String fileDir : fileDirList) {
                num++;
                FileInputStream fis = new FileInputStream(fileDir);

                ZipEntry entry = new ZipEntry("packed_save" + num + ".dat");
                zos.putNextEntry(entry);

                //Массив байтов
                byte[] buffer = new byte[fis.available()];
                zos.write(buffer);
                fis.close();
                zos.closeEntry();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
