package com.example.pitutur.Adapter;

public class ModelTamplate
{
    private String ID_Tamplate, Judul_Tamplate,
            Jenis_Tamplate, Isi_amplate, timestamp, uid;

    public ModelTamplate()
    {

    }

    public ModelTamplate(String ID_Tamplate, String judul_Tamplate,
                         String jenis_Tamplate, String isi_amplate,
                         String timestamp, String uid)
    {
        this.ID_Tamplate = ID_Tamplate;
        Judul_Tamplate = judul_Tamplate;
        Jenis_Tamplate = jenis_Tamplate;
        Isi_amplate = isi_amplate;
        this.timestamp = timestamp;
        this.uid = uid;
    }

    public String getID_Tamplate()
    {
        return ID_Tamplate;
    }

    public void setID_Tamplate(String ID_Tamplate)
    {
        this.ID_Tamplate = ID_Tamplate;
    }

    public String getJudul_Tamplate()
    {
        return Judul_Tamplate;
    }

    public void setJudul_Tamplate(String judul_Tamplate)
    {
        Judul_Tamplate = judul_Tamplate;
    }

    public String getJenis_Tamplate()
    {
        return Jenis_Tamplate;
    }

    public void setJenis_Tamplate(String jenis_Tamplate)
    {
        Jenis_Tamplate = jenis_Tamplate;
    }

    public String getIsi_amplate()
    {
        return Isi_amplate;
    }

    public void setIsi_amplate(String isi_amplate)
    {
        Isi_amplate = isi_amplate;
    }

    public String getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getUid()
    {
        return uid;
    }

    public void setUid(String uid)
    {
        this.uid = uid;
    }
}
