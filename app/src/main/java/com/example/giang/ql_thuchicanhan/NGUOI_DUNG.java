package com.example.giang.ql_thuchicanhan;

import java.util.Date;

/**
 * Created by nguye on 30/11/2016.
 */
class NGUOI_DUNG {
    int ID;
    String USER;
    String PASS;

    public NGUOI_DUNG(int ID, String USER, String PASS) {
        this.ID = ID;
        this.USER = USER;
        this.PASS = PASS;
    }
}

class LOAI_TAI_KHOAN {
    int ID;
    String TEN_LOAI;

    public LOAI_TAI_KHOAN(int ID, String TEN_LOAI) {
        this.ID = ID;
        this.TEN_LOAI = TEN_LOAI;
    }
}

class TAI_KHOAN {
    int ID;
    String TEN_TAI_KHOAN;
    int ID_NGUOI_DUNG;
    double SO_TIEN;
    Date NGAY_TAO;
    int LOAI_TAI_KHOAN;
    String GHI_CHU;

    public TAI_KHOAN(int ID, String TEN_TAI_KHOAN, int ID_NGUOI_DUNG, double SO_TIEN, Date NGAY_TAO, int LOAI_TAI_KHOAN, String GHI_CHU) {
        this.ID = ID;
        this.TEN_TAI_KHOAN = TEN_TAI_KHOAN;
        this.ID_NGUOI_DUNG = ID_NGUOI_DUNG;
        this.SO_TIEN = SO_TIEN;
        this.NGAY_TAO = NGAY_TAO;
        this.LOAI_TAI_KHOAN = LOAI_TAI_KHOAN;
        this.GHI_CHU = GHI_CHU;
    }
}

class LOAI_DM_CHI {
    int ID;
    String TEN_LOAI;

    public LOAI_DM_CHI(int ID, String TEN_LOAI) {
        this.ID = ID;
        this.TEN_LOAI = TEN_LOAI;
    }
}

class DM_CHI {
    int ID;
    int ID_LOAI;
    String MUC_CHI;

    public DM_CHI(int ID, int ID_LOAI, String MUC_CHI) {
        this.ID = ID;
        this.ID_LOAI = ID_LOAI;
        this.MUC_CHI = MUC_CHI;
    }
}

class DM_THU {
    int ID;
    String MUC_THU;

    public DM_THU(int ID, String MUC_THU) {
        this.ID = ID;
        this.MUC_THU = MUC_THU;
    }
}

class ND_CHI {
    int ID;
    int ID_MUC_CHI;
    double SO_TIEN;
    int TAI_KHOAN;
    Date NGAY_CHI;
    String CHI_CHO;
    String GHI_CHU;

    public ND_CHI(int ID, int ID_MUC_CHI, double SO_TIEN, int TAI_KHOAN, Date NGAY_CHI, String CHI_CHO, String GHI_CHU) {
        this.ID = ID;
        this.ID_MUC_CHI = ID_MUC_CHI;
        this.SO_TIEN = SO_TIEN;
        this.TAI_KHOAN = TAI_KHOAN;
        this.NGAY_CHI = NGAY_CHI;
        this.CHI_CHO = CHI_CHO;
        this.GHI_CHU = GHI_CHU;
    }
}

class ND_THU {
    int ID;
    int ID_MUC_THU;
    double SO_TIEN;
    double ID_TAI_KHOAN;
    Date NGAY_THU;
    String GHI_CHU;

    public ND_THU(int ID, int ID_MUC_THU, double SO_TIEN, double ID_TAI_KHOAN, Date NGAY_THU, String GHI_CHU) {
        this.ID = ID;
        this.ID_MUC_THU = ID_MUC_THU;
        this.SO_TIEN = SO_TIEN;
        this.ID_TAI_KHOAN = ID_TAI_KHOAN;
        this.NGAY_THU = NGAY_THU;
        this.GHI_CHU = GHI_CHU;
    }
}

