function selectBankLogo(bankId){
    var bankLogo = "";
    switch(bankId)
    {
        case 1://建设银行
            bankLogo="bank_logo_ccb.png";break;
        case 2://招商银行
            bankLogo="bank_logo_cmb.png";break;
        case 3://交通银行
            bankLogo="bank_logo_comm.png";break;
        case 4://中国银行
            bankLogo="bank_logo_boc.png";break;
        case 5://工商银行
            bankLogo="bank_logo_icbc.png";break;
        case 6://民生银行
            bankLogo="bank_logo_cmbc.png";break;
        case 7://浦发银行
            bankLogo="bank_logo_spdb.png";break;
        case 8://中信银行
            bankLogo="bank_logo_ecitic.png";break;
        case 9://农业银行
            bankLogo="bank_logo_abc.png";break;
        case 10://光大银行
            bankLogo="bank_logo_ceb.png";break;
        case 11://徽商银行
            bankLogo="bank_logo_hscb.png";break;
        case 12://邮政银行
            bankLogo="bank_logo_psbc.png";break;
        case 13://平安银行
            bankLogo="bank_logo_pa.png";break;
        case 14://广发银行
            bankLogo="bank_logo_cgb.png";break;
        case 15://华夏银行
            bankLogo="bank_logo_hxb.png";break;
    }
    return bankLogo;

}