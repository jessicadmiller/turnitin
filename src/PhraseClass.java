
//Handles the phrases and counts
public class PhraseClass {
    public String PhraseName;
    public int PhraseCount;

    public void setPhraseName(String name){
        PhraseName = name;
    }

    public String getPhraseName(){
        return PhraseName;
    }

    public void setPhraseCount(int count){
        PhraseCount = count;
    }

    public int getPhraseCount(){
        return PhraseCount;
    }

    public PhraseClass(String name, int count){
        setPhraseCount(count);
        setPhraseName(name);
    }
}
