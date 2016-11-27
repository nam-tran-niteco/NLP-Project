/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CheckVietnameseSpell;

/**
 *
 * @author Tran
 */
public abstract class Rule {

//    public abstract boolean checkInvalidate(String target);
    
    // 1: sắc, 2: huyền, 3: hỏi, 4: ngã, 5: nặng
    public char VnLexiName[] = {
        'a', 'á', 'à', 'ả', 'ã', 'ạ',
        'ă', 'ắ', 'ằ', 'ẳ', 'ẵ', 'ặ',
        'â', 'ấ', 'ầ', 'ẩ', 'ẫ', 'ậ',
        'b', 'c',
        'd', 'đ',
        'e', 'é', 'è', 'ẻ', 'ẽ', 'ẹ',
        'ê', 'ế', 'ề', 'ể', 'ễ', 'ệ',
        'g', 'h',
        'i', 'í', 'ì', 'ỉ', 'ĩ', 'ị',
        'k', 'l', 'm', 'n',
        'o', 'ó', 'ò', 'ỏ', 'õ', 'ọ',
        'ô', 'ố', 'ồ', 'ổ', 'ỗ', 'ộ',
        'ơ', 'ớ', 'ờ', 'ở', 'ỡ', 'ợ',
        'p', 'q', 'r', 's', 't',
        'u', 'ú', 'ù', 'ủ', 'ũ', 'ụ',
        'ư', 'ứ', 'ừ', 'ử', 'ữ', 'ự',
        'v', 'x'
    };

    public enum VowelSeq {

        vs_nil,
        vs_a,
        vs_ar,
        vs_ab,
        vs_e,
        vs_er,
        vs_i,
        vs_o,
        vs_or,
        vs_oh,
        vs_u,
        vs_uh,
        vs_y,
        vs_ai,
        vs_ao,
        vs_au,
        vs_ay,
        vs_aru,
        vs_ary,
        vs_eo,
        vs_eu,
        vs_eru,
        vs_ia,
        vs_ie,
        vs_ier,
        vs_iu,
        vs_oa,
        vs_oab,
        vs_oe,
        vs_oi,
        vs_ori,
        vs_ohi,
        vs_ua,
        vs_uar,
        vs_ue,
        vs_uer,
        vs_ui,
        vs_uo,
        vs_uor,
        vs_uoh,
        vs_uu,
        vs_uy,
        vs_uha,
        vs_uhi,
        vs_uho,
        vs_uhoh,
        vs_uhu,
        vs_ye,
        vs_yer,
        vs_ieu,
        vs_ieru,
        vs_oai,
        vs_oay,
        vs_oeo,
        vs_uay,
        vs_uary,
        vs_uoi,
        vs_uou,
        vs_uori,
        vs_uohi,
        vs_uohu,
        vs_uya,
        vs_uye,
        vs_uyer,
        vs_uyu,
        vs_uhoi,
        vs_uhou,
        vs_uhohi,
        vs_uhohu,
        vs_yeu,
        vs_yeru
    };

    public enum ConSeq {

        cs_nil,
        cs_b,
        cs_c,
        cs_ch,
        cs_d,
        cs_dd,
        cs_dz,
        cs_g,
        cs_gh,
        cs_gi,
        cs_gin,
        cs_h,
        cs_k,
        cs_kh,
        cs_l,
        cs_m,
        cs_n,
        cs_ng,
        cs_ngh,
        cs_nh,
        cs_p,
        cs_ph,
        cs_q,
        cs_qu,
        cs_r,
        cs_s,
        cs_t,
        cs_th,
        cs_tr,
        cs_v,
        cs_x
    };
    
}
