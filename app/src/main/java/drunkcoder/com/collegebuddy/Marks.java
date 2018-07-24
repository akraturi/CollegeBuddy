package drunkcoder.com.collegebuddy;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.JsonObject;

import java.util.List;

@Table(name="Marks")
public class Marks extends Model {
    @Column(name = "examName")
    String examName;
    @Column(name="scoredMarks")
    float scoredMarks;
    @Column(name="totalMarks")
    float totalMarks;
    @Column(name = "subjectId")
    long subjectId;

    public Marks()
    {
        super();
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public float getScoredMarks() {
        return scoredMarks;
    }

    public void setScoredMarks(float scoredMarks) {
        this.scoredMarks = scoredMarks;
    }

    public float getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(float totalMarks) {
        this.totalMarks = totalMarks;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public static String getOverallMarksForSubject(long subjectId)
    {
        float scoredMarks=0;
        float totalMarks = 0;

        List<Marks> marksList = Marks.getMarksForSubject(subjectId);

        for(int i=0;i<marksList.size();i++)
        {
            scoredMarks+=marksList.get(i).scoredMarks;
            totalMarks+=marksList.get(i).totalMarks;
        }

        return "Overall:"+scoredMarks+"/"+totalMarks;
    }

    public static List<Marks> getMarksForSubject(long id)
    {
        return new Select().from(Marks.class).where("subjectId=?",id).execute();
    }

    public JsonObject getJsonObject()
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("examName",examName);
        jsonObject.addProperty("scoredMarks",scoredMarks);
        jsonObject.addProperty("totalMarks",totalMarks);

        return jsonObject;
    }

    @Override
    public String toString() {
        return getJsonObject().toString();
    }


}
