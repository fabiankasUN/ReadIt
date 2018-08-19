package Model.services.Yandex.Yandex;


public class Request {

    private String code;
    private String lang;
    private String text[];

    public Request( String code, String lang, String text[]){
        this.code = code;
        this.lang= lang;
        this.text = text;
    }

    public String getCode(){
        return code;
    }

    public String lang(){
        return lang;
    }

    public String[] getText(){
        return text;
    }


}
