package com.oscar.exceptionsandlocalization;

import com.oscar.interfaces.Chapter;

import java.io.*;
import java.sql.SQLException;
import java.text.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

//Exception is an event that alters program flow
//Throwable class for all that events
//Overriden methods may not declare any new or broader exception
//Is allowed to declare fewer exceptions than the superclass or interface
public class ExceptionsAndLocalizations implements Chapter {
    @Override
    public void start() {
        System.out.println("Exceptions & Localizations");
        try {
            ChechedExceptions();
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        }

        UncheckedExceptions();

        ResourceManagment();

        FormattingValues();

        InternationalizationAndLocalization();

        ResourceBundles();

        Errors();
    }

    private void ResourceBundles() {
        //Resource bundle - contains the locale-specific objects to be used by a program it is like a KEY-MAP values

        Locale location = new Locale("en");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Zoo",location);

        System.out.println(resourceBundle.getObject("name"));

        ResourceBundle resourceBundleLocal = ResourceBundle.getBundle("Zoo");

        System.out.println(resourceBundleLocal.getObject("name"));

        //Order
        //Requested Locale
        //Language with no country
        //Default Locale
        //Default Locate with no country
        //No locale at all
        //MissingResourceException

        //If a property a key is not found it will try to get of any parent

        //Formatting Messages
        //MessageFormat for this
        String msg = resourceBundle.getString("message");

        System.out.println(MessageFormat.format(msg,"Oscar","HCL"));


        //Properties Class
        Properties properties = new Properties();
        properties.setProperty("name","Oscar");

        System.out.println(properties.getProperty("name"));
}

    private void InternationalizationAndLocalization() {
        //Internationalization is the process of designing your program, so it can be adapted
        //Localization support multiple locales or geographic regions
        //java.util.Local

        //The language is always required
        //The country is optional
        Locale locale = new Locale("en","US");//es_US\

        //Java let you create invalid lang or country
        Locale fakeLocation = new Locale("xx","YY");
        fakeLocation = new Locale.Builder().setLanguage("fr").setRegion("CA").build();

        //java.text.NumberFormat
        int precio = 3_200_000;
        NumberFormat numberFormat=NumberFormat.getInstance();
        System.out.println(numberFormat.format(precio));

        numberFormat=NumberFormat.getInstance(locale);
        System.out.println(numberFormat.format(precio));

        numberFormat=NumberFormat.getNumberInstance(locale);
        System.out.println(numberFormat.format(precio));

        numberFormat=NumberFormat.getCurrencyInstance(fakeLocation);
        System.out.println(numberFormat.format(precio));

        numberFormat=NumberFormat.getPercentInstance(fakeLocation);
        System.out.println(numberFormat.format(80));

        numberFormat=NumberFormat.getIntegerInstance(fakeLocation);
        System.out.println(numberFormat.format(98.8));

        //If you not specify Style by default is SHORT
        numberFormat=NumberFormat.getCompactNumberInstance(locale, NumberFormat.Style.SHORT);
        System.out.println(numberFormat.format(precio));

        numberFormat=NumberFormat.getCompactNumberInstance(locale, NumberFormat.Style.LONG);
        System.out.println(numberFormat.format(precio));


        //Parsing numbers
        //Convert String to a structured object

        try {
            //The return value of parse is a Number
            numberFormat=NumberFormat.getInstance();
            Double number=(Double) numberFormat.parse("40.4");
            System.out.println(number);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        //Localizing Dates
        //To localize Dates we can use DateTimeFormatter.whitLocale(location)
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        System.out.println(dtf.format(ldt)+"---"+dtf.withLocale(fakeLocation).format(ldt));

        //Specifying a Locale Category
        //You can set parts of the locale independently
        //DISPLAY - for displaying data about locale
        //FORMAT - formatting dates,numbers or currencies
        System.out.println(Locale.getDefault());
        Locale.setDefault(Locale.Category.DISPLAY,fakeLocation);
        System.out.println(Locale.getDefault());
        Locale.setDefault(Locale.Category.FORMAT,fakeLocation);
        System.out.println(Locale.getDefault());

    }

    private void FormattingValues() {
        //Formatting Numbers


        //NumberFormat is an interface, we need DecimalFormat to use it

        double d = 1234.56;

        //#  Omit the position if no digit exists
        //0  Put 0 in position if no digit exists
        NumberFormat format = new DecimalFormat("$###,###,###.00");
        System.out.println(format.format(d));


        //Formatting Dates and Times
        //LocalDateTime does not have a time zone specified

        LocalDateTime lDT= LocalDateTime.now();
        //Java provides a class for standard format DateTimeFormatter
        //will throw  an Exception if incompatible type
        System.out.println(lDT.format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println(lDT.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        System.out.println(lDT.format(DateTimeFormatter.ISO_LOCAL_TIME));

        //Customizing Date/Time format
        //DateTimeFormatter support custom format using a date format String
        //To escape single quote you can print ''
        var f = DateTimeFormatter.ofPattern("'Hoy es 'd' de 'MMMM' del 'yyyy 'Oscar''s'");
        //Datetime contain a format method
        System.out.println(lDT.format(f));
        //Formatter class contain a format method
        System.out.println(f.format(lDT));






    }

    private void ResourceManagment() {
        //Resource - Typically a file or DB that required some kind of stream or connection to read or write
        //Java includes the try-with-resources statement to auto close all resources opened in a try clause
        //One or more resources can be opened in the try clause - They will be close in reverse order
        //catch finally are  optional
        //Implicit finally run before any coded
        //Resource must be implemented AutoCloseable interface implementing close() method
        //You can declare resources as local variable
        //His scope it is only the try block
        //It is possible to declare them before provided (final or effectively final)
        //Required semicolon between resources

        File filePath = new File("C:\\Users\\camec\\IdeaProjects\\OCPJava17\\com\\oscar\\streams\\resourceEx.txt");

        try(FileReader fr = new FileReader(filePath);BufferedReader br = new BufferedReader(fr)){
            System.out.println(br.readLine());
        } catch (IOException e) {
            System.out.println(e);
        }
        finally {
            System.out.println("File closed");
        }



    }

    private void Errors() {
        //Your program should not attempt to recover from it
        //Throwable is the parent class
        //throw by the JVM

        //static initializer throws exception
        CatchExceptions(new ExceptionInInitializerError());

        //methods calls itself too many times
        CatchExceptions(new StackOverflowError());

        //class is not available in runtime
        CatchExceptions(new NoClassDefFoundError());

        throw new Error("Irrecoverable",new RuntimeException());

    }

    private void UncheckedExceptions() {
        //Does not to be declared or handled
        //RuntimeExceptios class is the superclass
        //Exception is an object you can store it as object reference
        RuntimeException runtimeException = new RuntimeException();

        //try to divide by zero
        CatchExceptions(new ArithmeticException());

        //Illegal index in an Array
        CatchExceptions(new ArrayIndexOutOfBoundsException());

        //Cast object to class which is not an instance
        CatchExceptions(new ClassCastException());

        //null when Object is required
        CatchExceptions(new NullPointerException());

        //throw by code to indicate illegal arg
        CatchExceptions(new IllegalArgumentException());

        //Subclass of IllegalArgumentException invalos format to parse int
        CatchExceptions(new NumberFormatException());
    }

    private void CatchExceptions(Throwable rTE){
        try{ //required event if one statement
            throw rTE;
        }//Java looks for order, if not compile error
        //Son to father
        //The firsts will catch the Exception
        catch (RuntimeException rte){
            System.out.println("===RUNTIME EXCEPTION===");
            System.out.println(rte);

        }
        catch (Exception exc){
            System.out.println("===EXCEPTION===");
            System.out.println(exc);
        }
        catch (Error err){
            System.out.println("===ERROR===");
            System.out.println(err);
        }
        catch (Throwable rt)
        {
            System.out.println(rt);
        }

        //Multicatch blocks for not related Exceptions
        try{
            throw rTE;
            //only a variable in the end
        } catch (ArithmeticException|ArrayIndexOutOfBoundsException|ClassCastException|ExceptionInInitializerError|FileNotFoundException e) {
            System.out.println(e);
        }
        catch (Throwable rt)
        {
            System.out.println(rt);
        }
        finally {
            System.out.println("FINALLY BLOCK");
        }
    }

    //throws to declare that the method might throw it and must be handled by caller
    private void ChechedExceptions() throws CustomException{
        //Must be declared or handle
        //All inherit Exception but not RuntimeException
        //Must be catched in a try-catch block

        try {
            //throw when you want to throw an Exception
            throw new CustomException("Error");
        }
        catch (CustomException e){
            //Print only the message
            System.out.println(e.getMessage());

            //Print exception header + message
            System.out.println(e);

            //Print exception all  trace
            e.printStackTrace();
        }



        //Checked Exception classes

        //Problem reading or writing resource file
        CatchExceptions(new IOException());

        //Sun of IOException When a reference file doest exist
        CatchExceptions(new FileNotFoundException());

        //Sun of IOException when try to serialize a not serializable class
        CatchExceptions(new NotSerializableException());

        //Problem parsing input
        CatchExceptions(new ParseException("not convertible",1));

        //SQLException Error related to SQL
        CatchExceptions(new SQLException());



        throw new CustomException("Error");

    }
}
