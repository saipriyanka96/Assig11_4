package com.example.layout.assig11_4;
//Package objects contain version information about the implementation and specification of a Java package
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // //here i have created main class
//public keyword is used in the declaration of a class,method or field;public classes,method and fields can be accessed by the members of any class.
//extends is for extending a class. implements is for implementing an interface
//AppCompatActivity is a class from e v7 appcompat library. This is a compatibility library that back ports some features of recent versions of
// Android to older devices.
    //variables
    DBhelper myDb;
    EditText firstName,lastName ,editTextId;
    Button btnAddData;
    Button btnviewAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Variables, methods, and constructors, which are declared protected in a superclass can be accessed only by the subclasses
        // in other package or any class within the package of the protected members class.
        //void is a Java keyword.  Used at method declaration and definition to specify that the method does not return any type,
        // the method returns void.
        //onCreate Called when the activity is first created. This is where you should do all of your normal static set up: create views,
        // bind data to lists, etc. This method also provides you with a Bundle containing the activity's previously frozen state,
        // if there was one.Always followed by onStart().
        //Bundle is most often used for passing data through various Activities.
// This callback is called only when there is a saved instance previously saved using onSaveInstanceState().
// We restore some state in onCreate() while we can optionally restore other state here, possibly usable after onStart() has
// completed.The savedInstanceState Bundle is same as the one used in onCreate().

        super.onCreate(savedInstanceState);
// call the super class onCreate to complete the creation of activity like the view hierarchy
        setContentView(R.layout.activity_main);
        //R means Resource
        //layout means design
        //  main is the xml you have created under res->layout->main.xml
        //  Whenever you want to change your current Look of an Activity or when you move from one Activity to another .
        // The other Activity must have a design to show . So we call this method in onCreate and this is the second statement to set
        // the design
        myDb = new DBhelper(this);
// creating the object for main activity
       //editText:A user interface element for entering and modifying text.
        //findViewById:A user interface element that displays text to the user.
        firstName = (EditText)findViewById(R.id.editText);
        lastName = (EditText)findViewById(R.id.editTextl);

        editTextId = (EditText)findViewById(R.id.edittextid);
        btnAddData = (Button)findViewById(R.id.button_add);
        btnviewAll = (Button)findViewById(R.id.button_viewAll);

        AddData();//adding the data
        //view all the data
        viewAll();

    }
    public  void AddData() {
        //onclick of the adddata button the data will get added in database
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(firstName.getText().toString(),
                                lastName.getText().toString());
                        //we add values by converting them into string
                        if(isInserted == true)
                            //if added the it will show dara is inserted otherwise no
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void viewAll() {
        //onclick of viewAll it will show the database whic we added
        btnviewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        // //This interface provides random read-write access to the result set returned by a database query.
                        //here we will get the data
                        //if count is 0 then it will show the message
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }
// The StringBuffer class is used to represent characters that can be modified.
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            //once o char is checked the it need to be moved to next char
                            buffer.append("Id :"+ res.getString(0)+"\n");
                            buffer.append("firstName :"+ res.getString(1)+"\n");
                            buffer.append("lastName :"+ res.getString(2)+"\n");
                            //this method returns a reference to this object.
                        }

                        // Show all data
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title,String Message){
        //to showMessage we need a alert dialog box
        //Creates a builder for an alert dialog that uses the default alert dialog theme.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        //Sets whether this dialog is cancelable with the BACK key.
        builder.setTitle(title);
        //set the title
        //set the message
        builder.setMessage(Message);
        builder.show();
        //view the data
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

