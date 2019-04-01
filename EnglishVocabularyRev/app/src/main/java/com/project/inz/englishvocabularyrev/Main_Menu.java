package com.project.inz.englishvocabularyrev;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import java.util.ArrayList;

public class Main_Menu extends Activity {

    ListView list;
    int selectedMode = -1;
    ListView listCategories;
    TextView textSelectedMode;
    Button buttonNext;


    String[] options = {
            "Fiszki",
            "Wpisz Słowo",
            "Quiz",
            "Statystyki",
            "Słownik",
            "Ustawienia",
            "Informacje"
    };
    String[] categories = {
            "Szkoła",
            "Zdrowie",
            "Dom",
            "Życie Rodzinne i Towarzyskie",
            "Praca"
    };
    Integer[] imageId = {
            R.drawable.ic_launcher_hdpi,
            R.drawable.ic_launcher_hdpi,
            R.drawable.ic_launcher_hdpi,
            R.drawable.ic_launcher_hdpi,
            R.drawable.ic_launcher_hdpi,
            R.drawable.ic_launcher_hdpi,
            R.drawable.ic_launcher_hdpi

    };

    /*Sprawzenie, które kategorie zostały wybrane*/
    public ArrayList<String> checkSelectedItems() {
        SparseBooleanArray checked = this.listCategories.getCheckedItemPositions();
        ArrayList<String> selectedCategories = new ArrayList<String>();
        for (int i = 0; i < categories.length; i++) {
            if (checked.get(i)) {
                selectedCategories.add(this.categories[i]);
//                Log.d("Selected: ", this.categories[i]);
            }

        }
        return selectedCategories;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__menu);

        /*INITIATING THE DATABASE*/
        DataBaseHelper myDbHelper = new DataBaseHelper(this);

        try {

            myDbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            myDbHelper.openDataBase();

        }catch(SQLException sqle){

            throw sqle;

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        /***/


        list = (ListView) findViewById(R.id.listMainMenu);
        listCategories = (ListView) findViewById(R.id.listCategoryMenu);
        textSelectedMode = (TextView) findViewById(R.id.textSelectedMode);
        buttonNext = (Button) findViewById(R.id.buttonNext);

        listCategories.setVisibility(View.INVISIBLE);
        textSelectedMode.setVisibility(View.INVISIBLE);
        buttonNext.setVisibility(View.INVISIBLE);


        /*Lista Main Menu*/
        CustomList adapter = new CustomList(Main_Menu.this, options, imageId);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                listCategories.setVisibility(View.VISIBLE);
                textSelectedMode.setVisibility(View.VISIBLE);
                textSelectedMode.setText("Wybrano tryb: " + options[+position]);
                buttonNext.setVisibility(View.VISIBLE);
                selectedMode = position;
//                Toast.makeText(Main_Menu.this, "You Clicked at " + options[+position], Toast.LENGTH_SHORT).show();

            }
        });

        /*Lista Categories*/
        ArrayAdapter adapter_listy = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, categories);
        listCategories.setAdapter(adapter_listy);
        listCategories.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        /*Obsługa przycisku dalej*/
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startActivityMode = null;

                switch (selectedMode) {
                    case 0:
                        Toast.makeText(Main_Menu.this, "Launching mode: " + selectedMode, Toast.LENGTH_SHORT).show();
                        checkSelectedItems();
                        startActivityMode = new Intent(Main_Menu.this, ModeFiszki.class);
                        break;
                    case 1:
                        Toast.makeText(Main_Menu.this, "Launching mode: " + selectedMode, Toast.LENGTH_SHORT).show();
                        checkSelectedItems();
                        startActivityMode = new Intent(Main_Menu.this, ModeWpiszSlowo.class);
                        break;
                    case 2:
                        Toast.makeText(Main_Menu.this, "Launching mode: " + selectedMode, Toast.LENGTH_SHORT).show();
                        checkSelectedItems();
                        startActivityMode = new Intent(Main_Menu.this, ModeQuiz.class);
                        break;
                    case 3:
                        Toast.makeText(Main_Menu.this, "Launching mode: " + selectedMode, Toast.LENGTH_SHORT).show();
                        startActivityMode = new Intent(Main_Menu.this, ModeFiszki.class);
                        break;
                    case 4:
                        Toast.makeText(Main_Menu.this, "Launching mode: " + selectedMode, Toast.LENGTH_SHORT).show();
                        startActivityMode = new Intent(Main_Menu.this, ModeFiszki.class);
                        break;
                    case 5:
                        Toast.makeText(Main_Menu.this, "Launching mode: " + selectedMode, Toast.LENGTH_SHORT).show();
                        startActivityMode = new Intent(Main_Menu.this, ModeFiszki.class);
                        break;
                    default:
                        break;
                }
                startActivity(startActivityMode);

            }
        });


    }
}
