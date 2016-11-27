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
    public char VnLexiName[] = {'!',
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
        'v', 'x',
        'y', 'ý', 'ỳ', 'ỷ', 'ỹ', 'ỵ'
    };
//    public String VnLexiName[] = {
//        "",
//        "a", "á", "à", "ả", "ã", "ạ",
//        "ă", "ắ", "ằ", "ẳ", "ẵ", "ặ",
//        "â", "ấ", "ầ", "ẩ", "ẫ", "ậ",
//        "b", "c",
//        "d", "đ",
//        "e", "é", "è", "ẻ", "ẽ", "ẹ",
//        "ê", "ế", "ề", "ể", "ễ", "ệ",
//        "g", "h",
//        "i", "í", "ì", "ỉ", "ĩ", "ị",
//        "k", "l", "m", "n",
//        "o", "ó", "ò", "ỏ", "õ", "ọ",
//        "ô", "ố", "ồ", "ổ", "ỗ", "ộ",
//        "ơ", "ớ", "ờ", "ở", "ỡ", "ợ",
//        "p", "q", "r", "s", "t",
//        "u", "ú", "ù", "ủ", "ũ", "ụ",
//        "ư", "ứ", "ừ", "ử", "ữ", "ự",
//        "v", "x",
//        "y", "ý", "ỳ", "ỷ", "ỹ", "ỵ"
//    };
    
    public int VNLEXICOUNT = VnLexiName.length;

    public String[] VowelSeq = {
        "",
        "a",
        "â",
        "ă",
        "e",
        "ê",
        "i",
        "o",
        "ô",
        "ơ",
        "u",
        "ư",
        "y",
        "ai",
        "ao",
        "au",
        "ay",
        "âu",
        "ây",
        "eo",
        "eu",
        "êu",
        "ia",
        "ie",
        "iê",
        "iu",
        "oa",
        "oă",
        "oe",
        "oi",
        "ôi",
        "ơi",
        "ua",
        "uâ",
        "ue",
        "uê",
        "ui",
        "uo",
        "uô",
        "uơ",
        "uu",
        "uy",
        "ưa",
        "ưi",
        "ưo",
        "ươ",
        "ưu",
        "ye",
        "yê",
        "ieu",
        "iêu",
        "oai",
        "oay",
        "oeo",
        "uay",
        "uây",
        "uoi",
        "uou",
        "uôi",
        "ươi",
        "uơu",
        "uya",
        "uye",
        "uyê",
        "uyu",
        "ưoi",
        "ưou",
        "ươi",
        "ươu",
        "yeu",
        "yêu"
    };
    public String[] ConSeq = {
        "",
        "b",
        "c",
        "ch",
        "d",
        "đ",
        "dz",
        "g",
        "gh",
        "gi",
        "gin",
        "h",
        "k",
        "kh",
        "l",
        "m",
        "n",
        "ng",
        "ngh",
        "nh",
        "p",
        "ph",
        "q",
        "qu",
        "r",
        "s",
        "t",
        "th",
        "tr",
        "v",
        "x"
    };

    
}
