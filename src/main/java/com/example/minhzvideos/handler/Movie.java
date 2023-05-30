package com.example.minhzvideos.handler;

import java.io.File;
import java.io.Serializable;

import com.example.minhzvideos.anno.Drillox;

@Drillox (Description = "To mimic the real videos")
public class Movie implements Serializable {
    private MType movieType;
    private String Name;
    private int Episodes;
    private int price;
    private File mDir;
    public boolean created;

    private IllegalArgumentException ilg = new IllegalArgumentException();

    public Movie(File _mDir){
        if (_mDir!=null && _isValid(_mDir)){
            this.mDir = _mDir;
            this.movieType = _getMovieType(_mDir);
            this.Name = _getName(_mDir);
            this.Episodes = _getEpisodes(_mDir);
            this.price = _getPrice(_mDir);
            double fileSize = _getFileSize(_mDir);
            this.created = true;
        }else
            this.created = false;
        // throw ilg;
    }

    public Movie(File _mDir,boolean isMusic){
        if (_mDir!=null && _isValidMusic(_mDir)){
            this.mDir = _mDir;
            this.movieType = _getMovieType(_mDir);
            this.Name = _getName(_mDir);
            this.Episodes = _getEpisodes(_mDir);
            this.price = _getPrice(_mDir);
            double fileSize = _getFileSize(_mDir);
            this.created = true;
        }else
            this.created = false;

    }

    public int getEpisodes(){
        return this.Episodes;
    }

    public int getPrice(){
        return this.price;
    }

    public String getName(){return this.Name;}
    public MType getMovieType(){return this.movieType;}

    private boolean _isValid(File mDir) {
        boolean isValid = false;
        if (Handler.SearchExt(mDir,".mkv") |
                Handler.SearchExt(mDir,".mp4")|
                Handler.SearchExt(mDir,".avi")){
            isValid = true;
        }
        return isValid;
    }

    private boolean _isValidMusic(File mDir) {
        boolean isValid = false;
        if (Handler.SearchExt(mDir,".mp3")){
            isValid = true;
        }
        return isValid;
    }
    private double _getFileSize(File mDir) {
        double _s = mDir.getTotalSpace();
        return _s;
    }

    private int _getPrice(File mDir) {
        if (this.movieType == MType.MOVIE){
            return 1000;
        }else {
            int ep = this.Episodes;
            if (ep<=10)
                return 1000;
            else if (ep<=20)
                return 2000;
            else if (ep<=30)
                return 3000;
            else if (ep<=40)
                return 4000;
            else return 5000;
        }
    }

    private int _getEpisodes(File mDir) {
        if (this.movieType == MType.MOVIE)
            return 1;
        else {
            int x = 0;
            x = Handler.Eps(mDir,".mkv");
            //x+=Handler.Eps(mDir,".mp4");
            x+=Handler.Eps(mDir,".avi");
            return x;
        }
    }

    private String _getName(File mDir) {
        return mDir.getName();
    }

    private MType _getMovieType(File mDir) {
        if(mDir.isDirectory())
            return MType.SERIE;
        else return MType.MOVIE;
    }

    public File getmDir(){
        return this.mDir;
    }


}
