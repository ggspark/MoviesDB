package com.fastacash.moviesdb.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Gaurav Gupta <gaurav@thegauravgupta.com>
 * @since 09/Dec/2014
 */

public class Utility {

    /**
     * Just pass in the InputStream and it will read the whole file and return it as a string
     * <p/>Eg: Utility.loadStringFromInputStream(getResources().openRawResource(R.raw.churches));
     *
     * @param is an InputStream which can be from a file or raw resource or asset
     *
     * @return String
     */
    public static String loadStringFromInputStream(InputStream is) {
        String st;
        try {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            st = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return st;

    }

    public static void ShowToast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    public static int convertDpToPixel(float dp, Context context) {

        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int px = (int) (dp * (metrics.densityDpi / 160f));
        return px;
    }

    public static boolean isValidEmailAddress(String email) {

        if (TextUtils.isEmpty(email)) {
            return false;
        }
        String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validateInputs(EditText editText, int length) {
        if (TextUtils.isEmpty(editText.getText().toString().trim())) {
            return false;
        }

        if (editText.getText().toString().length() == length)
            return true;
        else
            return false;
    }

    public static boolean validateInputs(EditText editText) {
        if (TextUtils.isEmpty(editText.getText().toString().trim())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Just pass in the String and it will return you the hexadecimal representation of md5 digest
     * <p/>Eg: Utility.md5("Hello");
     * <p/>Dependency: 'commons-codec:commons-codec:1.9'
     *
     * @param s The String to be digested
     *
     * @return md5 digest as hexadecimal String
     */
    public static String md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest(); //Gives us the MD5 digest of password
            //Hex.encodeHex() changes the byte array to its hexadecimal char array representation
            return new String(Hex.encodeHex(messageDigest));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Just pass in the String and it will return you the hexadecimal representation of sha1 digest
     * <p/>Eg: Utility.sha1("Hello");
     * <p/>Dependency: 'commons-codec:commons-codec:1.9'
     *
     * @param s The String to be digested
     *
     * @return sha1 digest as hexadecimal String
     */
    public static String sha1(final String s) {
        try {
            // Create SHA-1 Hash
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest(); //Gives us the SHA1 digest of password
            //Hex.encodeHex() changes the byte array to its hexadecimal char array representation
            return new String(Hex.encodeHex(messageDigest));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get the time elapsed since the date object passed
     *
     * @param pastDate
     *
     * @return String representation of Time Elapsed as 3 hour 4 min 5 sec
     */
    public static String getDateAgo(Date pastDate) {
        Date currentDate = new Date();
        long diffInMillisec = currentDate.getTime() - pastDate.getTime();
        long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMillisec);
        long seconds = diffInSec % 60;
        diffInSec /= 60;
        long minutes = diffInSec % 60;
        diffInSec /= 60;
        long hours = diffInSec % 24;
        diffInSec /= 24;
        long days = diffInSec;

        String st = "";
        if (days > 0) {
            st += days + " day ";
        }
        if (hours > 0) {
            st += hours + " hour ";
        }
        if (minutes > 0) {
            st += minutes + " min ";
        }
        if (seconds > 0) {
            st += seconds + " sec";
        }

        return st;
    }

    /**
     * Get the date in String format
     *
     * @param date
     *
     * @return date in String format as 0:28 AM, 12 Jan 2015
     */
    public static String getDateTime(Date date) {
        String st;
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        st = calendar.get(Calendar.HOUR) + ":" +
                calendar.get(Calendar.MINUTE) + " " +
                calendar.getDisplayName(Calendar.AM_PM, Calendar.SHORT, Locale.US) + ", " +

                (calendar.get(Calendar.DATE) + 1) + " " +
                calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US) + " " +
                calendar.get(Calendar.YEAR);


        return st;
    }


    /**
     * @param context
     *
     * @return IMEI for GSM and MEID for CDMA
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }


    /**
     * @param context
     *
     * @return
     */
    public static String getPhoneNumber(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getLine1Number();
    }

    /**
     * @param context
     *
     * @return
     */
    public static String getPhoneOperator(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getNetworkOperatorName();
    }


    /**
     * @param context
     *
     * @return
     */
    public static List<String> getImagePaths(Context context) {
        ContentResolver cr = context.getContentResolver();
        Cursor cursor;
        LinkedList<String> paths = new LinkedList<String>();
        String[] projection = {MediaStore.Images.Media.DATA};
        cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                paths.add(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
            }
        }
        cursor.close();
        return paths;
    }

    /**
     * @param context
     * @param path
     *
     * @return the thumbnail of the bitmap of given path, null if path is invalid
     */
    public static Bitmap getImageThumbnails(Context context, String path) {
        Bitmap bitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(path), 90, 90);
        return bitmap;
    }

    /**
     * @param context
     * @param path
     *
     * @return the full bitmap of given path, null if path is invalid
     */
    public static Bitmap getImageFull(Context context, String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        return bitmap;
    }

    /**
     * @param context
     * @param path
     *
     * @return the bitmap of given path scaled by half, null if path is invalid
     */
    public static Bitmap getImageHalf(Context context, String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if (bitmap == null) {
            return null;
        } else {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        }
        return bitmap;
    }

    /**
     * @param context
     * @param path
     *
     * @return the bitmap of given path scaled if more than 1000, null if path is invalid
     */
    public static Bitmap getImageReasonable(Context context, String path) {
        Bitmap bitmap = setScaledBitmap(path, 1000, 1000);
        if (bitmap == null) {
            return null;
        } else {
            int larger = (bitmap.getWidth() > bitmap.getHeight()) ? bitmap.getWidth() : bitmap.getHeight();

            if (larger > 1000) {
                int scale = larger / 1000 + 1;
                bitmap = ThumbnailUtils.extractThumbnail(bitmap, bitmap.getWidth() / scale, bitmap.getHeight() / scale);

            } else {
                bitmap = ThumbnailUtils.extractThumbnail(bitmap, bitmap.getWidth(), bitmap.getHeight());
            }
        }
        return bitmap;
    }

    /**
     * Apply HD image to the given file path with scaled bitmap
     * @param filePath
     */
    public static Bitmap setScaledBitmap(String filePath,int reqWidth,int reqHeight ) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return getRotatedImage(BitmapFactory.decodeFile(filePath, options), filePath);
    }

    /**
     * If image is rotated it rotates the image back
     * @param myBitmap
     * @param path
     * @return
     */
    public static Bitmap getRotatedImage(Bitmap myBitmap, String path) {
        try {
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            } else if (orientation == 3) {
                matrix.postRotate(180);
            } else if (orientation == 8) {
                matrix.postRotate(270);
            } else {
            }
            myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(), matrix, true); // rotating bitmap
        } catch (Exception e) {

        }

        return myBitmap;
    }

    /**
     * Gets the calculated sample size to reduce bitmap
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        final int halfHeight = height / 2;
        final int halfWidth = width / 2;

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while ((halfHeight / inSampleSize) > reqHeight
                && (halfWidth / inSampleSize) > reqWidth) {
            inSampleSize *= 2;
        }

        return inSampleSize;
    }

    /**
     * Get the last known location from all providers return best reading that is as accurate as minAccuracy meters and was taken no longer than minAge milliseconds ago, if none, return null.
     * If any of the parameters is less than or equal to zero, return best possible reading
     *
     * @param context
     * @param minAccuracy in meters
     * @param maxAge      in milliseconds
     *
     * @return Location
     */
    public static Location getLastKnownLocation(Context context, float minAccuracy, long maxAge) {

        Location bestResult = null;
        float bestAccuracy = Float.MAX_VALUE;
        long bestAge = Long.MIN_VALUE;
        LocationManager locationManager;

        List<String> matchingProviders = (locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE)).getAllProviders();

        for (String provider : matchingProviders) {

            Location location = locationManager.getLastKnownLocation(provider);

            if (location != null) {

                float accuracy = location.getAccuracy();
                long time = location.getTime();

                if (accuracy < bestAccuracy) {

                    bestResult = location;
                    bestAccuracy = accuracy;
                    bestAge = time;

                }
            }
        }

        if (minAccuracy > 0 && maxAge > 0) {
            // Return best reading or null
            if (bestAccuracy > minAccuracy || (System.currentTimeMillis() - bestAge) > maxAge) {
                return null;
            } else {
                return bestResult;
            }
        } else {
            return bestResult;
        }
    }

    /**
     * @param context
     *
     * @return
     */
    public static List<Contact> getContacts(Context context) {
        ContentResolver cr = context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        LinkedList<Contact> contacts = new LinkedList<Contact>();
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                Contact contact = new Contact();
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                contact.name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    //Get phone numbers
                    Cursor phoneCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    LinkedList<String> phoneNumber = new LinkedList<String>();
                    while (phoneCur.moveToNext()) {
                        String phoneNo = phoneCur.getString(phoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phoneNumber.add(phoneNo);
                    }
                    contact.phoneNumbers = phoneNumber.toArray(new String[phoneNumber.size()]);
                    phoneCur.close();
                    //Get email addresses
                    Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                    LinkedList<String> emailAddress = new LinkedList<String>();
                    while (emailCur.moveToNext()) {
                        String email = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        emailAddress.add(email);
                    }
                    contact.emailAddresses = emailAddress.toArray(new String[emailAddress.size()]);
                    emailCur.close();
                    contacts.add(contact);
                }
            }
        }
        return contacts;
    }

    public static class Contact {
        public Contact() {
        }

        public String name;
        public String[] phoneNumbers;
        public String[] emailAddresses;

        public String toJson() {
            return (new Gson()).toJson(this);
        }

        public static Contact fromJson(String json) {
            return (new Gson()).fromJson(json, Contact.class);
        }

        public static List<JSONObject> toJsonObjectList(List<Contact> contacts) {
            List<JSONObject> jContacts = new ArrayList<JSONObject>(contacts.size());
            for (Contact contact : contacts) {
                try {
                    jContacts.add(new JSONObject(contact.toJson()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return jContacts;
        }

        public static List<Contact> fromJsonObjectList(List<JSONObject> jContacts) {
            List<Contact> contacts = new ArrayList<Contact>(jContacts.size());
            for (JSONObject jContact : jContacts) {
                contacts.add(Contact.fromJson(jContact.toString()));
            }
            return contacts;
        }

    }

    public static class Image {
        public Image() {
        }

        public String devicePath;
        public String url;
        public String type;

        public String toJson() {
            return (new Gson()).toJson(this);
        }

        public static Image fromJson(String json) {
            return (new Gson()).fromJson(json, Image.class);
        }

        public static List<JSONObject> toJsonObjectList(List<Image> images) {
            List<JSONObject> jImages = new ArrayList<JSONObject>(images.size());
            for (Image image : images) {
                try {
                    jImages.add(new JSONObject(image.toJson()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return jImages;
        }

        public static List<Image> fromJsonObjectList(List<JSONObject> jImages) {
            List<Image> images = new ArrayList<Image>(jImages.size());
            for (JSONObject jImage : jImages) {
                images.add(Image.fromJson(jImage.toString()));
            }
            return images;
        }
    }

    public interface MethodCallBack {
        public void refreshSpinner();
    }

}

