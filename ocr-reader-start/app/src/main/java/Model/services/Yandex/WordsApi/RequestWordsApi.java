package Model.services.Yandex.WordsApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class RequestWordsApi implements Serializable{

    private String word;
    private List<Result> results;
    private Syllable syllables;
    private Pronunciation pronunciation;
    private float frecuency;




    public class Syllable implements Serializable{
        private int count;
        private String[] list;
    }
    public class Pronunciation implements Serializable{
        public String all;
    }

    public class Result implements Serializable{
        private String definition;
        private String partOfSpeech;
        private String[] examples;


    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Syllable getSyllables() {
        return syllables;
    }

    public void setSyllables(Syllable syllables) {
        this.syllables = syllables;
    }

    public Pronunciation getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(Pronunciation pronunciation) {
        this.pronunciation = pronunciation;
    }

    public float getFrecuency() {
        return frecuency;
    }

    public void setFrecuency(float frecuency) {
        this.frecuency = frecuency;
    }
}
