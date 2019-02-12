package ca.javateacher.filedemo1;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

class FileUtils {

  private static String LOG_APP_TAG = "FileDemo1>FileUtils";

  static void save(Context context, String fileName, String data) {
    try(FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        PrintWriter output = new PrintWriter(outputStream)){
      output.print(data);
    } catch (IOException e) {
      Log.e(LOG_APP_TAG, e.getMessage());
    }
  }

  static String load(Context context, String fileName){
    StringBuilder ret = new StringBuilder();
    try(FileInputStream inputStream = context.openFileInput(fileName);
        Scanner input = new Scanner(inputStream)){
      while(input.hasNextLine()){
        ret.append(input.nextLine());
      }
    }catch (IOException e) {
      Log.e(LOG_APP_TAG, e.getMessage());
    }
    return ret.toString();
  }




}
