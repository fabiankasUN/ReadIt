package Model.services.Yandex.Glosbe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class RequestGlosbe implements Serializable{

    private String result;
    private List<Meaning> tuc;
    private List<Example> examples;

    public class Meaning implements Serializable{
        private Phrase phrase;
        private List<Mean> meanings;
        private long meaningId;
        private long[] authors;

        public Phrase getPhrase(){
            return phrase;
        }

        public ArrayList<Mean> meanings(){
            return (ArrayList<Mean>)meanings;
        }

    }

    public String getTranslate(){
        return tuc.get(0).phrase.text;
    }

    public List<Meaning> getMeanings(){
        return tuc;
    }

    public class Mean implements Serializable{
        private String text;
        private String language;

        public String getLanguage(){
            return language;
        }

        public String getText(){
            return text;
        }
    }


    public class Phrase implements Serializable{
        private String text;
        private String language;

        public String getLanguage(){
            return language;
        }

        public String getText(){
            return text;
        }

    }

    public class Example implements Serializable{
        String first;
        String second;

        public String getFirst(){
            return first;
        }
        public String getSecond(){
            return second;
        }
    }


    public ArrayList<Example> getExamples(){
        return (ArrayList) examples;
    }


}
