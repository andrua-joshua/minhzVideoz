package com.example.minhzvideos.handler;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

import com.example.minhzvideos.anno.Drillox;

@Drillox(Description = "For handling all the program logic " +
        "and act as an API of it")
public class Handler {

    private static ArrayList<File> Dirs = new ArrayList<File>();

    public static TreeMap<String, ArrayList<Movie>> AllMovies
            = new TreeMap<>();
    public static TreeMap<String, ArrayList<Movie>> AllMusic
            = new TreeMap<>();

    public static Setting settings = new Setting(false,
            false, false,
            false, false,
            false, "12356");

    @Drillox(Description = "For doing all the file research from the fs")
    public static void Reloading() {
    }

    @Drillox(Description = "For doing all the file research from the fl provided")
    public static void Reloading(File fil, boolean addON) {
//        addON = true;
        ArrayList<Movie> Movies = new ArrayList<>();
        ArrayList<Movie> Music = new ArrayList<>();
        System.out.println("Initial Movies Size::::: " + Movies.size());
        if (Dirs.isEmpty()) Dirs.add(fil);
        File fl;
        while (!Dirs.isEmpty()) {
            fl = Dirs.get(0);
            if (fl.isDirectory()) {

                Movie mv;
                if ((mv = new Movie(fl)).created) {
                    Movies.add(mv);
                    Dirs.remove(fl);
                } else {
                    if (fl.listFiles() != null) {
                        for (File f : fl.listFiles()) {
                            Dirs.add(f);
                        }
                        Dirs.remove(fl);
                    } else {
                        Dirs.remove(fl);
                    }
                }
            } else {
                Movie mv;
                if ((mv = new Movie(fl, true)).created) {
                    Music.add(mv);
                    mv = null;
                    Dirs.remove(fl);
                }
                if ((mv = new Movie(fl)).created) {
                    Movies.add(mv);
                    mv = null;
                    Dirs.remove(fl);
                } else {
                    Dirs.remove(fl);
                }

            }
        }

        if (addON) {
            if (!Movies.isEmpty()) {
                AllMovies.put(fil.getAbsolutePath(), Movies);
                AllMusic.put(fil.getAbsolutePath(), Music);
            }
        } else {
            AllMovies.clear();
            AllMusic.clear();
            if (!Movies.isEmpty()) {
                AllMovies.put(fil.getAbsolutePath(), Movies);
                AllMusic.put(fil.getAbsolutePath(), Music);
            }
        }

    }

    public static boolean SearchExt(File _fl, String ext) {
        boolean isPresent = false;
        //Search for the existance of the extension
        if (_fl.isDirectory()) {
            int count = 0;
            if (_fl.listFiles() != null) {
                ArrayList<String> _nms = new ArrayList<String>();
                for (File fl : _fl.listFiles()) {
                    if (!fl.isDirectory()) {
                        String _ext = "";
                        int _i = fl.getName().lastIndexOf('.');
                        if (_i != -1) _ext = fl.getName().substring(_i).trim();
                        boolean _isPresent = (_ext.equals(ext));
                        if (_isPresent) {
                            int l = fl.getName().indexOf('.');
                            _nms.add(fl.getName().substring(0, l));
                            count++;
                        }
                    }
                }
                if (count > 1) {
                    boolean isSame = false;
                    int c = 0;
                    for (String str : _nms) {
                        if (c > 0 && (isSame = str
                                .toLowerCase().equals(_nms.get(0)
                                        .toLowerCase()))) {
                            break;
                        }
                        c++;
                    }
                    isPresent = isSame;
                }
            }
        } else {
            String _ext = "";
            int _i = _fl.getName().lastIndexOf('.');
            if (_i != -1) _ext = _fl.getName().substring(_i).trim();
            isPresent = (_ext.equals(ext));
        }
        return isPresent;
    }

    public static int Eps(File _fl, String ext) {
        int _count = 0;
        //code to do all the counting
        if (_fl.listFiles() != null) {
            for (File fl : _fl.listFiles()) {
                String _ext = "";
                int _i = fl.getName().lastIndexOf('.');
                if (_i != -1) _ext = fl.getName().substring(_i).trim();
                boolean _isPresent = (_ext.equals(ext));
                if (_isPresent) _count++;
            }
        }
        return _count;
    }

    public static String getMovieDetails(Movie mv) {
        // 66-maxFileName
        String _str = mv.getEpisodes() + "Eps   ";
        if (_str.length() == 8) _str += mv.getPrice() + "Ugx   ";
        else _str += " " + mv.getPrice() + "Ugx   ";
        _str += mv.getName();
        String str = _str;
        _str = mv.getMovieType().toString() + "   " + str;

        return _str;
    }

    public static ArrayList<Movie> searchMovie(String _mvNm) {
        ArrayList<Movie> mvlist = new ArrayList<>();
        String flname = "";

        Set<String> keySet = AllMovies.keySet();
        for (String key : keySet) {
            for (Movie mv : AllMovies.get(key)) {
                if (mv.getMovieType() == MType.SERIE) {
                    for (File _fl : mv.getmDir().listFiles()) {
                        flname = _fl
                                .getName();
                    }
                } else flname = mv.getName();

                if (hasVal(flname, _mvNm))
                    mvlist.add(mv);
            }
        }
        System.out.println("inside search Func: " + mvlist.size());
        return mvlist;
    }

    public static ArrayList<Movie> searchMusic(String _mvNm) {
        ArrayList<Movie> mvlist = new ArrayList<>();
        String flname = "";

        Set<String> keySet = AllMusic.keySet();
        for (String key : keySet) {
            for (Movie mv : AllMusic.get(key)) {
                flname = mv.getName();

                if (hasVal(flname, _mvNm))
                    mvlist.add(mv);
            }
        }
        System.out.println("inside search Func: " + mvlist.size());
        return mvlist;
    }

    public static boolean hasVal(String _val, String Against) {
        boolean statment = false;
        int str_Leng = _val.length();
        int ag_Leng = Against.length();

        int _i = -1;

        if (Against.length() != 0)
            for (int i = 0; i < _val.length(); i++) {
                System.out.println("Fieles from current index: "+_val.substring(i));
                if (_val.toLowerCase().charAt(i)
                        == Against.toLowerCase().charAt(0)
                        && /*(str_Leng - i + 1)*/
                            _val.substring(i).length()>= ag_Leng) {

                    String nStr = _val.toLowerCase()
                            .substring(i, i + ag_Leng);
                    System.out.println(_val.substring(i)+"::::::>>>>>>>>> "+nStr
                                +" :::::::::: typed>> "+Against);
                    if (nStr.equals(Against.toLowerCase())) {
                        _i = 1;
                        break;
                    }
                }
            }


        if (_i != -1)
            statment = true;

        return statment;
    }

    public static ArrayList<Movie> searchMovie(String _mvNm, int type) {
        ArrayList<Movie> mvlist = new ArrayList<>();
        String flname = "";

        Set<String> keySet = AllMovies.keySet();
        for (String key : keySet) {
            for (Movie mv : AllMovies.get(key)) {
                if (type == 0) {
                    if (mv.getMovieType() == MType.SERIE) {
                        for (File _fl : mv.getmDir().listFiles()) {
                            flname = _fl
                                    .getName();
                        }
                    } else flname = mv.getName();
                } else if (type == 1) {
                    if (mv.getMovieType() == MType.MOVIE) {
                        flname = mv.getName();
                    }
                } else if (type == 2) {
                    if (mv.getMovieType() == MType.SERIE) {
                        for (File _fl : mv.getmDir().listFiles()) {
                            flname = _fl
                                    .getName();
                        }
                    }
                }

                if (hasVal(flname, _mvNm))
                    mvlist.add(mv);
            }
        }
        System.out.println("inside search Func: " + mvlist.size());
        return mvlist;
    }

    public static int deleteFile(File fl) {
        int deleted = -1;
        //the deleting process

        return deleted;
    }

    public static Integer copyFile(File fltCpy, File location) {
        Integer progressInt = 0;
        //the copying mechanism here
        return progressInt;
    }

    public static ArrayList<Movie> getMovies(int i) {
        ArrayList<Movie> _mvs = new ArrayList<>();

        Set<String> keySet = AllMovies.keySet();
        for (String key : keySet) {
            for (Movie mv : AllMovies.get(key)) {
                if (i == 1) {
                    if (mv.getMovieType() == MType.MOVIE)
                        _mvs.add(mv);
                } else if (i == 2) {
                    if (mv.getMovieType() == MType.SERIE) _mvs.add(mv);
                } else {
                    _mvs.add(mv);
                }
            }
        }
        System.out.println("Size after:: " + _mvs.size());
        return _mvs;
    }

    public static ArrayList<Movie> getMusic() {
        ArrayList<Movie> _mvs = new ArrayList<>();

        Set<String> keySet = AllMusic.keySet();
        for (String key : keySet) {
            for (Movie mv : AllMusic.get(key)) {
                _mvs.add(mv);
            }
        }
        System.out.println("Music Size after:: " + _mvs.size());
        return _mvs;
    }

    public static void saveSearchLogs(TreeMap<String, ArrayList<Movie>> allMovies,boolean isMovies)
            throws IOException {
        File logfile;
        if (isMovies)
            logfile = new File(System.getProperty("user.home"),
                    ".dlogs.dat");
        else
            logfile = new File(System.getProperty("user.home"),
                    ".dMusiclogs.dat");
        if (!logfile.exists())
            logfile.createNewFile();
        FileOutputStream fout = new FileOutputStream(logfile);
        ObjectOutputStream obout = new ObjectOutputStream(fout);
        obout.writeObject(allMovies);
        obout.flush();
        obout.close();

    }

    public static void saveSettings() throws IOException {
        File logfile = new File(System.getProperty("user.home"),
                ".dsettings.dat");
        if (!logfile.exists())
            logfile.createNewFile();
        FileOutputStream fout = new FileOutputStream(logfile);
        ObjectOutputStream obout = new ObjectOutputStream(fout);
        obout.writeObject(settings);
        obout.flush();
        obout.close();
    }

    public static void loadfromDLogs() throws IOException, ClassNotFoundException {
        //System.out.println("loading from the logs::");
        File logfile = new File(System.getProperty("user.home"),
                ".dlogs.dat");
        File logfile2 = new File(System.getProperty("user.home"),
                ".dMusiclogs.dat");

        FileInputStream fin = new FileInputStream(logfile);
        ObjectInputStream obin = new ObjectInputStream(fin);

        FileInputStream fin2 = new FileInputStream(logfile2);
        ObjectInputStream obin2 = new ObjectInputStream(fin2);

        AllMovies = (TreeMap<String, ArrayList<Movie>>)
                obin.readObject();

        AllMusic = (TreeMap<String, ArrayList<Movie>>)
                obin2.readObject();

        fin.close();
        fin2.close();
        obin2.close();
        obin.close();

        if (AllMovies == null) {
            AllMovies = new TreeMap<>();
            System.out.println("AllMovies == null");
        }

        if (AllMusic == null) {
            AllMusic = new TreeMap<>();
            System.out.println("AllMusic == null");
        }

        int size = 0;
        Set<String> keys = AllMusic.keySet();
        for (String key: keys){
            size += AllMusic.get(key).size();
        }
        System.out.println("AllMusic Size On Loading : "+size);

    }

    public static String pathRefactor(String path) {
        String str = "";

        int firstIndexOf_ = path.indexOf(' ');
        String _buf_lft = path.substring(0, firstIndexOf_);
        String _buf_rht = path.substring(firstIndexOf_);
        int _lstindex = _buf_lft.lastIndexOf('/');
        int _frstIndex = _buf_rht.indexOf('/');
        String _str = path.substring(_lstindex + 1);
        String _buf2 = _buf_lft.substring(0, _lstindex);
        System.out.println("=======>" + _str);

        str = _buf2 + "/'" + _str + "'";
        return str;
    }

    public static void initSettings() throws IOException, ClassNotFoundException {
        File settingFile = new File(System.getProperty("user.home"),
                ".dsettings.dat");
        FileInputStream fin = new FileInputStream(settingFile);
        ObjectInputStream obin = new ObjectInputStream(fin);

        settings = (Setting)obin.readObject();

        fin.close();
        obin.close();

        if(settings == null){
            settings = new Setting(false,
                    false, false,
                    false, false,
                    false, "12356");
        }
    }

    public static void saveSearchLogsToBackUp(TreeMap<String, ArrayList<Movie>> allMovies,boolean isMovies)
            throws IOException {
        File logfile;
        if (isMovies)
            logfile = new File(System.getProperty("user.home"),
                    ".dBackUplogs.dat");
        else
            logfile = new File(System.getProperty("user.home"),
                    ".dMusicBackUplogs.dat");
        if (!logfile.exists())
            logfile.createNewFile();
        FileOutputStream fout = new FileOutputStream(logfile);
        ObjectOutputStream obout = new ObjectOutputStream(fout);
        obout.writeObject(allMovies);
        obout.flush();
        obout.close();

    }

    public static void rollBackToBackUp() throws IOException, ClassNotFoundException {
        //System.out.println("loading from the logs::");
        File logfile = new File(System.getProperty("user.home"),
                ".dBackUplogs.dat");
        File logfile2 = new File(System.getProperty("user.home"),
                ".dMusicBackUplogs.dat");

        FileInputStream fin = new FileInputStream(logfile);
        ObjectInputStream obin = new ObjectInputStream(fin);

        FileInputStream fin2 = new FileInputStream(logfile2);
        ObjectInputStream obin2 = new ObjectInputStream(fin2);

        AllMovies = (TreeMap<String, ArrayList<Movie>>)
                obin.readObject();

        AllMusic = (TreeMap<String, ArrayList<Movie>>)
                obin2.readObject();

        fin.close();
        fin2.close();
        obin2.close();
        obin.close();

        if (AllMovies == null) {
            AllMovies = new TreeMap<>();
            System.out.println("AllMovies == null");
        }

        if (AllMusic == null) {
            AllMusic = new TreeMap<>();
            System.out.println("AllMusic == null");
        }

        int size = 0;
        Set<String> keys = AllMusic.keySet();
        for (String key: keys){
            size += AllMusic.get(key).size();
        }
        System.out.println("AllMusic Size On Loading : "+size);

    }

}
