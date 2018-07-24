
package drunkcoder.com.collegebuddy;

import android.util.Log;

import com.activeandroid.serializer.TypeSerializer;

import java.util.List;

public class ListTypeSerializer extends TypeSerializer {

    private static final String TAG = ListTypeSerializer.class.getSimpleName();


    @Override
    public Class<?> getDeserializedType() {
        return List.class;
    }


    @Override
    public Class<?> getSerializedType() {
        return String.class;
    }


    @Override
    public String serialize(Object data) {

        Log.i(TAG, "in serialize");

        StringBuilder sBuilder = new StringBuilder();

        // If we succesfully convert it to a String, print out the resulting string,
        // otherwise print an error
        if(getListAsJsonString(data, sBuilder)){
            Log.i(TAG, "serialize: " + sBuilder.toString());
        }
        else{
            Log.e(TAG, "Error ");
        }

        return sBuilder.toString();
    }


    private boolean getListAsJsonString(Object o, StringBuilder sBuilder){

        if(o instanceof List){
            Log.i(TAG, "getListType is a List");

            List<?> list = (List<?>) o;

            if(!list.isEmpty()){
                Object type = list.get(0);

                if(type instanceof Marks){
                    List<Marks> finalList = (List<Marks>) list;
                    sBuilder.append("{\"")
                            .append("MarksList")
                            .append("\":")
                            .append(finalList.toString())
                            .append("}");
                    return true;
                }
            }
        }

        return false;
    }


    @Override
    public List<?> deserialize(Object data) {

        Log.i(TAG, "in deserialize");

        String s = (String) data;

        // Get our wheelList using our global gson instance
        if(s.contains("MarksList")){
            GetMarksList gsl = MyApplication
                    .getApp()
                    .getGson()
                    .fromJson(s, GetMarksList.class);

            return gsl.marksList;
        }
        else {
            return null;
        }
    }

    public static class GetMarksList {
       public List<Marks> marksList;
    }
}