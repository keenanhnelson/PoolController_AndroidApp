package com.example.poolcontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    Button myButton1, myButton2, myButton3, myPlusButton, myMenuButton, myLeftButton, myRightButton, myMinusButton, DisplayBeforeImageButton, DisplayAfterImageButton, TakeImageButton, DisplayTakenImageButton;
    TextView myTextView1;
    ImageView myBeforeImage, myAfterImage, TakenImageView;
    //int ButtonTrigger = 8;//Nothing=8 FirstB=0, SecondB=1, ThirdB=2, PlusB=3, MenuB=4, RightB=5, LeftB=6, MinusB=7
    String ButtonTrigger;
    int toggleBeforeImage = 0, toggleAfterImage = 0;
    int serverPort;
    String ip;
    EditText IP_Address_Input, editTextPortNumber;
    String ipAndPortFilename = "ipAndPort.txt";

    void saveIpAndPortInfoToFile(){
        FileOutputStream outputStream;
        ip = IP_Address_Input.getText().toString();
        serverPort = Integer.parseInt(editTextPortNumber.getText().toString());
        try {
            outputStream = openFileOutput(ipAndPortFilename, MODE_PRIVATE);
            outputStream.write(ip.getBytes());
            outputStream.write("\n".getBytes());
            outputStream.write(String.valueOf(serverPort).getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IP_Address_Input = (EditText)findViewById(R.id.IP_Address);
        editTextPortNumber = (EditText)findViewById(R.id.editTextPortNumber);
        myButton1 = (Button)findViewById(R.id.myButton1);
        myButton2 = (Button)findViewById(R.id.myButton2);
        myButton3 = (Button)findViewById(R.id.myButton3);
        myPlusButton = (Button)findViewById(R.id.myPlusButton);
        myMenuButton = (Button)findViewById(R.id.myMenuButton);
        myLeftButton = (Button)findViewById(R.id.myLeftButton);
        myRightButton = (Button)findViewById(R.id.myRightButton);
        myMinusButton = (Button)findViewById(R.id.myMinusButton);
        DisplayBeforeImageButton = (Button)findViewById(R.id.DisplayBeforeImageButton);
        DisplayAfterImageButton = (Button)findViewById(R.id.DisplayAfterImageButton);
        myBeforeImage = (ImageView)findViewById(R.id.myBeforeImageView);
        myAfterImage = (ImageView)findViewById(R.id.myAfterImageView);
        TakeImageButton = (Button)findViewById(R.id.TakeImageButton);
        DisplayTakenImageButton = (Button)findViewById(R.id.DisplayTakenImageButton);
        TakenImageView = (ImageView)findViewById(R.id.TakenImageView);

        myBeforeImage.setImageResource(R.drawable.beforeone);
        myAfterImage.setImageResource(R.drawable.afterone);
        myBeforeImage.setVisibility(View.INVISIBLE);
        toggleBeforeImage = 1;
        myAfterImage.setVisibility(View.INVISIBLE);
        toggleAfterImage = 1;

        myTextView1 = (TextView)findViewById(R.id.myTextView1);

        //Read any previous ip and port information
        FileInputStream inputStream;
        BufferedReader reader;
        try {
            inputStream = openFileInput(ipAndPortFilename);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            ip = reader.readLine();
            serverPort = Integer.parseInt(reader.readLine());
            IP_Address_Input.setText(ip);
            editTextPortNumber.setText(String.valueOf(serverPort));
            inputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        myButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonTrigger = "One";
                Thread myThread = new Thread(r);
                myThread.start();
            }
        });

        myButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonTrigger = "Two";
                Thread myThread = new Thread(r);
                myThread.start();
            }
        });

        myButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonTrigger = "Three";
                Thread myThread = new Thread(r);
                myThread.start();
            }
        });

        myPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonTrigger = "Plus";
                Thread myThread = new Thread(r);
                myThread.start();
            }
        });

        myMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonTrigger = "Menu";
                Thread myThread = new Thread(r);
                myThread.start();
            }
        });

        myLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonTrigger = "Left";
                Thread myThread = new Thread(r);
                myThread.start();
            }
        });

        myRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonTrigger = "Right";
                Thread myThread = new Thread(r);
                myThread.start();
            }
        });

        myMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonTrigger = "Minus";
                Thread myThread = new Thread(r);
                myThread.start();
            }
        });
        TakeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonTrigger = "TakeImage";
                Thread myThread = new Thread(TakeImageThread);
                myThread.start();
            }
        });

        DisplayBeforeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakenImageView.setVisibility(View.INVISIBLE);
                myBeforeImage.setVisibility(View.VISIBLE);
                myAfterImage.setVisibility(View.INVISIBLE);

                DisplayTakenImageButton.setClickable(true);
                DisplayBeforeImageButton.setClickable(false);
                DisplayAfterImageButton.setClickable(true);

                DisplayTakenImageButton.setAlpha(1f);
                DisplayBeforeImageButton.setAlpha(.5f);
                DisplayAfterImageButton.setAlpha(1f);
            }
        });

        DisplayAfterImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakenImageView.setVisibility(View.INVISIBLE);
                myBeforeImage.setVisibility(View.INVISIBLE);
                myAfterImage.setVisibility(View.VISIBLE);

                DisplayTakenImageButton.setClickable(true);
                DisplayBeforeImageButton.setClickable(true);
                DisplayAfterImageButton.setClickable(false);

                DisplayTakenImageButton.setAlpha(1f);
                DisplayBeforeImageButton.setAlpha(1f);
                DisplayAfterImageButton.setAlpha(.5f);
            }
        });
        DisplayTakenImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakenImageView.setVisibility(View.VISIBLE);
                myBeforeImage.setVisibility(View.INVISIBLE);
                myAfterImage.setVisibility(View.INVISIBLE);

                DisplayTakenImageButton.setClickable(false);
                DisplayBeforeImageButton.setClickable(true);
                DisplayAfterImageButton.setClickable(true);

                DisplayTakenImageButton.setAlpha(.5f);
                DisplayBeforeImageButton.setAlpha(1f);
                DisplayAfterImageButton.setAlpha(1f);
            }
        });

    }
    Runnable TakeImageThread = new Runnable(){
        public void run(){
            Socket s = null;
            try{
                runOnUiThread(new Runnable() {
                    public void run() {
                        myTextView1.setText("Message has been sent");
                    }
                });

                ip = IP_Address_Input.getText().toString();
                serverPort = Integer.parseInt(editTextPortNumber.getText().toString());
                saveIpAndPortInfoToFile();
                String data = ButtonTrigger + "\n";//"From Java Client\n";
                s = new Socket(ip, serverPort);
                DataInputStream input = new DataInputStream( s.getInputStream());
                DataOutputStream output = new DataOutputStream( s.getOutputStream());
                output.writeBytes(data);

                //Reading information about file about to be transferred
                String digit;//where the information about the file is put
                digit = input.readLine();
                String myFilename = digit;

                digit = input.readLine();
                int myFileSize = Integer.parseInt(digit);

                digit = input.readLine();
                String Empty = digit;

                // receive file
                int count, TotalCount=0;
                byte[] buffer = new byte[6022386];//needs to be large enough to fit the entire file
                while(TotalCount < myFileSize){
                    count = input.read(buffer, TotalCount, 8192);
                    TotalCount += count;
                }

                //Display image
                final Bitmap bmp = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
                runOnUiThread(new Runnable() {
                    public void run() {
                        TakenImageView.setImageBitmap(Bitmap.createScaledBitmap(bmp, TakenImageView.getWidth(), TakenImageView.getHeight(), false));
                        DisplayTakenImageButton.performClick();
                        //TakenImageView.setVisibility(View.VISIBLE);
                        //toggleBeforeImage = 0;
                    }
                });


                //send confirmation message to server that image was received
                data = "confirm\n";
                output.writeBytes(data);

                //Wait for Server to send confirmation message back
                digit = input.readLine();//Waiting for button press completion
                final String st = digit;//new String(digit);
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), st, Toast.LENGTH_LONG).show();
                        myTextView1.setText(st);
                    }
                });
            }
            catch (UnknownHostException e){
                Log.e("Sock:", e.getMessage(), e);}
            catch (EOFException e){
                Log.e("EOF:", e.getMessage(), e); }
            catch (IOException e){
                Log.e("IO:", e.getMessage(), e);}
            finally {
                if(s!=null)
                    try {s.close();
                    }
                    catch (IOException e) {}
            }
        }
    };
    Runnable r = new Runnable(){

        public void run() {
            Socket s = null;
            try{
                runOnUiThread(new Runnable() {
                    public void run() {
                        myTextView1.setText("Message has been sent");
                    }
                });

                ip = IP_Address_Input.getText().toString();
                serverPort = Integer.parseInt(editTextPortNumber.getText().toString());
                saveIpAndPortInfoToFile();
                String data = ButtonTrigger + "\n";//"From Java Client\n";
                s = new Socket(ip, serverPort);
                DataInputStream input = new DataInputStream( s.getInputStream());
                DataOutputStream output = new DataOutputStream( s.getOutputStream());
                output.writeBytes(data);

                //Reading information about file about to be transferred
                String digit;//where the information about the file is put
                digit = input.readLine();
                String myFilename = digit;

                digit = input.readLine();
                int myFileSize = Integer.parseInt(digit);

                digit = input.readLine();
                String Empty = digit;

                // receive file
                int count, TotalCount=0;
                byte[] buffer = new byte[6022386];//needs to be large enough to fit the entire file
                while(TotalCount < myFileSize){
                    count = input.read(buffer, TotalCount, 8192);
                    TotalCount += count;
                }

                //Display image
                final Bitmap bmp = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
                runOnUiThread(new Runnable() {
                    public void run() {
                        myBeforeImage.setImageBitmap(Bitmap.createScaledBitmap(bmp, myBeforeImage.getWidth(), myBeforeImage.getHeight(), false));
                        DisplayBeforeImageButton.performClick();
                        //myBeforeImage.setVisibility(View.VISIBLE);
                        //toggleBeforeImage = 0;
                    }
                });




                //Receive after picture
                digit = input.readLine();
                myFilename = digit;

                digit = input.readLine();
                myFileSize = Integer.parseInt(digit);

                digit = input.readLine();
                Empty = digit;

                // receive file
                count = 0;
                TotalCount=0;
                //byte[] buffer = new byte[6022386];//needs to be large enough to fit the entire file
                while(TotalCount < myFileSize){
                    count = input.read(buffer, TotalCount, 8192);
                    TotalCount += count;
                }

                //Display image
                final Bitmap bmp2 = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
                runOnUiThread(new Runnable() {
                    public void run() {
                        myAfterImage.setImageBitmap(Bitmap.createScaledBitmap(bmp2, myAfterImage.getWidth(), myAfterImage.getHeight(), false));
                        DisplayAfterImageButton.performClick();
                        //myAfterImage.setVisibility(View.VISIBLE);
                        //toggleAfterImage = 0;
                    }
                });







                //send confirmation message to server that image was received
                data = "confirm\n";
                output.writeBytes(data);

                //Wait for Server to send confirmation message back
                digit = input.readLine();//Waiting for button press completion
                final String st = digit;//new String(digit);
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), st, Toast.LENGTH_LONG).show();
                        myTextView1.setText(st);
                    }
                });
            }
            catch (UnknownHostException e){
                Log.e("Sock:", e.getMessage(), e);
            }
            catch (EOFException e){
                Log.e("EOF:", e.getMessage(), e);
            }
            catch (IOException e){
                Log.e("IO:", e.getMessage(), e);
            }
            finally {
                if(s != null) {
                    try {
                        s.close();
                    }
                    catch (IOException e) {
                        Log.e("IO:", e.getMessage(), e);
                    }
                }
            }
        }
    };

}