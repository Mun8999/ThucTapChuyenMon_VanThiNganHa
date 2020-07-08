package ultil;

public class getConnect {
    private String host;
    private String url;

    public getConnect setHost(String host) {
        this.host = host;
        return null;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String ma) {
        if(ma=="d"){
            this.url="http://"+host+"/data/";
        }else if(ma=="ib")
            this.url="http://"+host+"/img/icon/black/";
        else if(ma=="iw")
            this.url="http://"+host+"/img/icon/white/";
        else if(ma=="dmb"){
            this.url="http://"+host+"/img/image/danhmuc/black/";
        }else if(ma=="dmw")
            this.url="http://"+host+"/img/image/danhmuc/white/";
        else if(ma=="sp")
            this.url="http://"+host+"/img/image/sanpham/";
        else
            this.url="";
    }
}
