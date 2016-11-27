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
public class InvalidVietnameseRule extends Rule {

    public char UnicodeTable[]
            = {0x0041, 0x0061, 0x00c1, 0x00e1, 0x00c0, 0x00e0, 0x1ea2, 0x1ea3, 0x00c3, 0x00e3, 0x1ea0, 0x1ea1, //a
                0x00c2, 0x00e2, 0x1ea4, 0x1ea5, 0x1ea6, 0x1ea7, 0x1ea8, 0x1ea9, 0x1eaa, 0x1eab, 0x1eac, 0x1ead, //a^
                0x0102, 0x0103, 0x1eae, 0x1eaf, 0x1eb0, 0x1eb1, 0x1eb2, 0x1eb3, 0x1eb4, 0x1eb5, 0x1eb6, 0x1eb7, //a(
                0x0042, 0x0062, 0x0043, 0x0063, 0x0044, 0x0064, //B b C c D d
                0x0110, 0x0111, // DD, dd
                0x0045, 0x0065, 0x00c9, 0x00e9, 0x00c8, 0x00e8, 0x1eba, 0x1ebb, 0x1ebc, 0x1ebd, 0x1eb8, 0x1eb9, //e
                0x00ca, 0x00ea, 0x1ebe, 0x1ebf, 0x1ec0, 0x1ec1, 0x1ec2, 0x1ec3, 0x1ec4, 0x1ec5, 0x1ec6, 0x1ec7, //e^
                /*0x0046, 0x0066,*/ 0x0047, 0x0067, 0x0048, 0x0068, // F f G g H h
                0x0049, 0x0069, 0x00cd, 0x00ed, 0x00cc, 0x00ec, 0x1ec8, 0x1ec9, 0x0128, 0x0129, 0x1eca, 0x1ecb, //i
                /*0x004a, 0x006a,*/ 0x004b, 0x006b, 0x004c, 0x006c, 0x004d, 0x006d, 0x004e, 0x006e, // J j K k L l M m N n
                0x004f, 0x006f, 0x00d3, 0x00f3, 0x00d2, 0x00f2, 0x1ece, 0x1ecf, 0x00d5, 0x00f5, 0x1ecc, 0x1ecd, //o
                0x00d4, 0x00f4, 0x1ed0, 0x1ed1, 0x1ed2, 0x1ed3, 0x1ed4, 0x1ed5, 0x1ed6, 0x1ed7, 0x1ed8, 0x1ed9, //o^
                0x01a0, 0x01a1, 0x1eda, 0x1edb, 0x1edc, 0x1edd, 0x1ede, 0x1edf, 0x1ee0, 0x1ee1, 0x1ee2, 0x1ee3, //o+
                0x0050, 0x0070, 0x0051, 0x0071, 0x0052, 0x0072, 0x0053, 0x0073, 0x0054, 0x0074, //P p Q q R r S s T t
                0x0055, 0x0075, 0x00da, 0x00fa, 0x00d9, 0x00f9, 0x1ee6, 0x1ee7, 0x0168, 0x0169, 0x1ee4, 0x1ee5, //u
                0x01af, 0x01b0, 0x1ee8, 0x1ee9, 0x1eea, 0x1eeb, 0x1eec, 0x1eed, 0x1eee, 0x1eef, 0x1ef0, 0x1ef1, //u+
                0x0056, 0x0076, /*0x0057, 0x0077,*/ 0x0058, 0x0078, // V v W w X x
                0x0059, 0x0079, 0x00dd, 0x00fd, 0x1ef2, 0x1ef3, 0x1ef6, 0x1ef7, 0x1ef8, 0x1ef9, 0x1ef4, 0x1ef5, //y
                /*0x005a, 0x007a,*/ // Z z
                // Symbols that have different code points in Unicode and Western charsets
                0x20AC, 0x20A1, /*0x0192,*/ 0x201E, 0x2026, 0x2020, 0x2021, 0x02C6,
                0x2030, 0x0160, 0x2039, 0x0152, 0x017D, 0x2018, 0x2019, 0x201C,
                0x201D, 0x2022, 0x2013, 0x2014, 0x02DC, 0x2122, 0x0161, 0x203A,
                0x0153, 0x017E, 0x0178};

    public boolean binarySearch(int key, int[] array) {
        int size = array.length;
        int low = 0;
        int high = size - 1;

        while (high > low) {
            int middle = (low + high) / 2;
            if (array[middle] == key) {
                return true;
            }
            if (array[middle] < key) {
                low = middle + 1;
            }
            if (array[middle] > key) {
                high = middle - 1;
            }
        }
        return false;
    }

    /**
     *
     */
    class VowelSeqInfo {

        int len; // độ dài chuỗi nguyên âm (tối đa là 3)
        int complete; // chưa hiểu cái này lắm vì có cái chuỗi nguyên âm không tồn tại mà complete vẫn bằng 1
        int conSuffix; //allow consonnant suffix
        VnLexiName v[]; // những kí tự tạo thành chuỗi nguyên âm
        VowelSeq sub[]; // chưa hiểu chuỗi này đại diện cho cái gì

        int roofPos;
        VowelSeq withRoof;

        int hookPos;
        VowelSeq withHook; //hook & bowl

        public VowelSeqInfo() {
        }

        public VowelSeqInfo(int len, int complete, int conSuffix, VnLexiName[] v, VowelSeq[] sub, int roofPos, VowelSeq withRoof, int hookPos, VowelSeq withHook) {
            this.len = len;
            this.complete = complete;
            this.conSuffix = conSuffix;
            this.v = v;
            this.sub = sub;
            this.roofPos = roofPos;
            this.withRoof = withRoof;
            this.hookPos = hookPos;
            this.withHook = withHook;
        }
    }

    /**
     *
     */
    class ConSeqInfo {

        int len;
        VnLexiName c[];
        boolean suffix; // có cho 

        public ConSeqInfo() {
        }

        public ConSeqInfo(int len, VnLexiName[] c, boolean suffix) {
            this.len = len;
            this.c = c;
            this.suffix = suffix;
        }
    };

    VowelSeqInfo VSeqList[] = {
        new VowelSeqInfo(1, 1, 1, new VnLexiName[]{VnLexiName.vnl_a, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_a, VowelSeq.vs_nil, VowelSeq.vs_nil}, -1, VowelSeq.vs_ar, -1, VowelSeq.vs_ab),
        new VowelSeqInfo(1, 1, 1, new VnLexiName[]{VnLexiName.vnl_ar, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_ar, VowelSeq.vs_nil, VowelSeq.vs_nil}, 0, VowelSeq.vs_nil, -1, VowelSeq.vs_ab),
        new VowelSeqInfo(1, 1, 1, new VnLexiName[]{VnLexiName.vnl_ab, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_ab, VowelSeq.vs_nil, VowelSeq.vs_nil}, -1, VowelSeq.vs_ar, 0, VowelSeq.vs_nil),
        new VowelSeqInfo(1, 1, 1, new VnLexiName[]{VnLexiName.vnl_e, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_e, VowelSeq.vs_nil, VowelSeq.vs_nil}, -1, VowelSeq.vs_er, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(1, 1, 1, new VnLexiName[]{VnLexiName.vnl_er, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_er, VowelSeq.vs_nil, VowelSeq.vs_nil}, 0, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(1, 1, 1, new VnLexiName[]{VnLexiName.vnl_i, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_i, VowelSeq.vs_nil, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(1, 1, 1, new VnLexiName[]{VnLexiName.vnl_o, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_o, VowelSeq.vs_nil, VowelSeq.vs_nil}, -1, VowelSeq.vs_or, -1, VowelSeq.vs_oh),
        new VowelSeqInfo(1, 1, 1, new VnLexiName[]{VnLexiName.vnl_or, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_or, VowelSeq.vs_nil, VowelSeq.vs_nil}, 0, VowelSeq.vs_nil, -1, VowelSeq.vs_oh),
        new VowelSeqInfo(1, 1, 1, new VnLexiName[]{VnLexiName.vnl_oh, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_oh, VowelSeq.vs_nil, VowelSeq.vs_nil}, -1, VowelSeq.vs_or, 0, VowelSeq.vs_nil),
        new VowelSeqInfo(1, 1, 1, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_nil, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_uh),
        new VowelSeqInfo(1, 1, 1, new VnLexiName[]{VnLexiName.vnl_uh, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_uh, VowelSeq.vs_nil, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, 0, VowelSeq.vs_nil),
        new VowelSeqInfo(1, 1, 1, new VnLexiName[]{VnLexiName.vnl_y, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_y, VowelSeq.vs_nil, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_a, VnLexiName.vnl_i, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_a, VowelSeq.vs_ai, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_a, VnLexiName.vnl_o, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_a, VowelSeq.vs_ao, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_a, VnLexiName.vnl_u, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_a, VowelSeq.vs_au, VowelSeq.vs_nil}, -1, VowelSeq.vs_aru, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_a, VnLexiName.vnl_y, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_a, VowelSeq.vs_ay, VowelSeq.vs_nil}, -1, VowelSeq.vs_ary, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_ar, VnLexiName.vnl_u, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_ar, VowelSeq.vs_aru, VowelSeq.vs_nil}, 0, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_ar, VnLexiName.vnl_y, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_ar, VowelSeq.vs_ary, VowelSeq.vs_nil}, 0, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_e, VnLexiName.vnl_o, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_e, VowelSeq.vs_eo, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(2, 0, 0, new VnLexiName[]{VnLexiName.vnl_e, VnLexiName.vnl_u, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_e, VowelSeq.vs_eu, VowelSeq.vs_nil}, -1, VowelSeq.vs_eru, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_er, VnLexiName.vnl_u, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_er, VowelSeq.vs_eru, VowelSeq.vs_nil}, 0, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_i, VnLexiName.vnl_a, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_i, VowelSeq.vs_ia, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(2, 0, 1, new VnLexiName[]{VnLexiName.vnl_i, VnLexiName.vnl_e, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_i, VowelSeq.vs_ie, VowelSeq.vs_nil}, -1, VowelSeq.vs_ier, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(2, 1, 1, new VnLexiName[]{VnLexiName.vnl_i, VnLexiName.vnl_er, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_i, VowelSeq.vs_ier, VowelSeq.vs_nil}, 1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_i, VnLexiName.vnl_u, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_i, VowelSeq.vs_iu, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(2, 1, 1, new VnLexiName[]{VnLexiName.vnl_o, VnLexiName.vnl_a, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_o, VowelSeq.vs_oa, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_oab),
        new VowelSeqInfo(2, 1, 1, new VnLexiName[]{VnLexiName.vnl_o, VnLexiName.vnl_ab, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_o, VowelSeq.vs_oab, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, 1, VowelSeq.vs_nil),
        new VowelSeqInfo(2, 1, 1, new VnLexiName[]{VnLexiName.vnl_o, VnLexiName.vnl_e, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_o, VowelSeq.vs_oe, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_o, VnLexiName.vnl_i, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_o, VowelSeq.vs_oi, VowelSeq.vs_nil}, -1, VowelSeq.vs_ori, -1, VowelSeq.vs_ohi),
        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_or, VnLexiName.vnl_i, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_or, VowelSeq.vs_ori, VowelSeq.vs_nil}, 0, VowelSeq.vs_nil, -1, VowelSeq.vs_ohi),
        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_oh, VnLexiName.vnl_i, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_oh, VowelSeq.vs_ohi, VowelSeq.vs_nil}, -1, VowelSeq.vs_ori, 0, VowelSeq.vs_nil),
        new VowelSeqInfo(2, 1, 1, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_a, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_ua, VowelSeq.vs_nil}, -1, VowelSeq.vs_uar, -1, VowelSeq.vs_uha),
        new VowelSeqInfo(2, 1, 1, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_ar, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uar, VowelSeq.vs_nil}, 1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(2, 0, 1, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_e, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_ue, VowelSeq.vs_nil}, -1, VowelSeq.vs_uer, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(2, 1, 1, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_er, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uer, VowelSeq.vs_nil}, 1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_i, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_ui, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_uhi),
        new VowelSeqInfo(2, 0, 1, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_o, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uo, VowelSeq.vs_nil}, -1, VowelSeq.vs_uor, -1, VowelSeq.vs_uho),
        new VowelSeqInfo(2, 1, 1, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_or, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uor, VowelSeq.vs_nil}, 1, VowelSeq.vs_nil, -1, VowelSeq.vs_uoh),
        new VowelSeqInfo(2, 1, 1, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_oh, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uoh, VowelSeq.vs_nil}, -1, VowelSeq.vs_uor, 1, VowelSeq.vs_uhoh),
        new VowelSeqInfo(2, 0, 0, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_u, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uu, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_uhu),
        new VowelSeqInfo(2, 1, 1, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_y, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uy, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_uh, VnLexiName.vnl_a, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_uh, VowelSeq.vs_uha, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, 0, VowelSeq.vs_nil),
        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_uh, VnLexiName.vnl_i, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_uh, VowelSeq.vs_uhi, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, 0, VowelSeq.vs_nil),
        new VowelSeqInfo(2, 0, 1, new VnLexiName[]{VnLexiName.vnl_uh, VnLexiName.vnl_o, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_uh, VowelSeq.vs_uho, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, 0, VowelSeq.vs_uhoh),
        new VowelSeqInfo(2, 1, 1, new VnLexiName[]{VnLexiName.vnl_uh, VnLexiName.vnl_oh, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_uh, VowelSeq.vs_uhoh, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, 0, VowelSeq.vs_nil),
        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_uh, VnLexiName.vnl_u, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_uh, VowelSeq.vs_uhu, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, 0, VowelSeq.vs_nil),
        new VowelSeqInfo(2, 0, 1, new VnLexiName[]{VnLexiName.vnl_y, VnLexiName.vnl_e, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_y, VowelSeq.vs_ye, VowelSeq.vs_nil}, -1, VowelSeq.vs_yer, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(2, 1, 1, new VnLexiName[]{VnLexiName.vnl_y, VnLexiName.vnl_er, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_y, VowelSeq.vs_yer, VowelSeq.vs_nil}, 1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(3, 0, 0, new VnLexiName[]{VnLexiName.vnl_i, VnLexiName.vnl_e, VnLexiName.vnl_u}, new VowelSeq[]{VowelSeq.vs_i, VowelSeq.vs_ie, VowelSeq.vs_ieu}, -1, VowelSeq.vs_ieru, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(3, 1, 0, new VnLexiName[]{VnLexiName.vnl_i, VnLexiName.vnl_er, VnLexiName.vnl_u}, new VowelSeq[]{VowelSeq.vs_i, VowelSeq.vs_ier, VowelSeq.vs_ieru}, 1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(3, 1, 0, new VnLexiName[]{VnLexiName.vnl_o, VnLexiName.vnl_a, VnLexiName.vnl_i}, new VowelSeq[]{VowelSeq.vs_o, VowelSeq.vs_oa, VowelSeq.vs_oai}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(3, 1, 0, new VnLexiName[]{VnLexiName.vnl_o, VnLexiName.vnl_a, VnLexiName.vnl_y}, new VowelSeq[]{VowelSeq.vs_o, VowelSeq.vs_oa, VowelSeq.vs_oay}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(3, 1, 0, new VnLexiName[]{VnLexiName.vnl_o, VnLexiName.vnl_e, VnLexiName.vnl_o}, new VowelSeq[]{VowelSeq.vs_o, VowelSeq.vs_oe, VowelSeq.vs_oeo}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(3, 0, 0, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_a, VnLexiName.vnl_y}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_ua, VowelSeq.vs_uay}, -1, VowelSeq.vs_uary, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(3, 1, 0, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_ar, VnLexiName.vnl_y}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uar, VowelSeq.vs_uary}, 1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(3, 0, 0, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_o, VnLexiName.vnl_i}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uo, VowelSeq.vs_uoi}, -1, VowelSeq.vs_uori, -1, VowelSeq.vs_uhoi),
        new VowelSeqInfo(3, 0, 0, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_o, VnLexiName.vnl_u}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uo, VowelSeq.vs_uou}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_uhou),
        new VowelSeqInfo(3, 1, 0, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_or, VnLexiName.vnl_i}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uor, VowelSeq.vs_uori}, 1, VowelSeq.vs_nil, -1, VowelSeq.vs_uohi),
        new VowelSeqInfo(3, 0, 0, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_oh, VnLexiName.vnl_i}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uoh, VowelSeq.vs_uohi}, -1, VowelSeq.vs_uori, 1, VowelSeq.vs_uhohi),
        new VowelSeqInfo(3, 0, 0, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_oh, VnLexiName.vnl_u}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uoh, VowelSeq.vs_uohu}, -1, VowelSeq.vs_nil, 1, VowelSeq.vs_uhohu),
        new VowelSeqInfo(3, 1, 0, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_y, VnLexiName.vnl_a}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uy, VowelSeq.vs_uya}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(3, 0, 1, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_y, VnLexiName.vnl_e}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uy, VowelSeq.vs_uye}, -1, VowelSeq.vs_uyer, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(3, 1, 1, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_y, VnLexiName.vnl_er}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uy, VowelSeq.vs_uyer}, 2, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(3, 1, 0, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_y, VnLexiName.vnl_u}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uy, VowelSeq.vs_uyu}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(3, 0, 0, new VnLexiName[]{VnLexiName.vnl_uh, VnLexiName.vnl_o, VnLexiName.vnl_i}, new VowelSeq[]{VowelSeq.vs_uh, VowelSeq.vs_uho, VowelSeq.vs_uhoi}, -1, VowelSeq.vs_nil, 0, VowelSeq.vs_uhohi),
        new VowelSeqInfo(3, 0, 0, new VnLexiName[]{VnLexiName.vnl_uh, VnLexiName.vnl_o, VnLexiName.vnl_u}, new VowelSeq[]{VowelSeq.vs_uh, VowelSeq.vs_uho, VowelSeq.vs_uhou}, -1, VowelSeq.vs_nil, 0, VowelSeq.vs_uhohu),
        new VowelSeqInfo(3, 1, 0, new VnLexiName[]{VnLexiName.vnl_uh, VnLexiName.vnl_oh, VnLexiName.vnl_i}, new VowelSeq[]{VowelSeq.vs_uh, VowelSeq.vs_uhoh, VowelSeq.vs_uhohi}, -1, VowelSeq.vs_nil, 0, VowelSeq.vs_nil),
        new VowelSeqInfo(3, 1, 0, new VnLexiName[]{VnLexiName.vnl_uh, VnLexiName.vnl_oh, VnLexiName.vnl_u}, new VowelSeq[]{VowelSeq.vs_uh, VowelSeq.vs_uhoh, VowelSeq.vs_uhohu}, -1, VowelSeq.vs_nil, 0, VowelSeq.vs_nil),
        new VowelSeqInfo(3, 0, 0, new VnLexiName[]{VnLexiName.vnl_y, VnLexiName.vnl_e, VnLexiName.vnl_u}, new VowelSeq[]{VowelSeq.vs_y, VowelSeq.vs_ye, VowelSeq.vs_yeu}, -1, VowelSeq.vs_yeru, -1, VowelSeq.vs_nil),
        new VowelSeqInfo(3, 1, 0, new VnLexiName[]{VnLexiName.vnl_y, VnLexiName.vnl_er, VnLexiName.vnl_u}, new VowelSeq[]{VowelSeq.vs_y, VowelSeq.vs_yer, VowelSeq.vs_yeru}, 1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil)
    };

    /**
     * Các chuỗi phụ âm có thể có
     */
    ConSeqInfo CSeqList[] = {
        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_b, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, true),
        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_c, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, true),
        new ConSeqInfo(2, new VnLexiName[]{VnLexiName.vnl_c, VnLexiName.vnl_h, VnLexiName.vnl_nonVnChar}, true),
        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_d, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, false),
        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_dd, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, false),
        new ConSeqInfo(2, new VnLexiName[]{VnLexiName.vnl_d, VnLexiName.vnl_z, VnLexiName.vnl_nonVnChar}, false),
        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_g, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, false),
        new ConSeqInfo(2, new VnLexiName[]{VnLexiName.vnl_g, VnLexiName.vnl_h, VnLexiName.vnl_nonVnChar}, false),
        new ConSeqInfo(2, new VnLexiName[]{VnLexiName.vnl_g, VnLexiName.vnl_i, VnLexiName.vnl_nonVnChar}, false),
        new ConSeqInfo(3, new VnLexiName[]{VnLexiName.vnl_g, VnLexiName.vnl_i, VnLexiName.vnl_n}, false),
        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_h, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, false),
        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_k, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, false),
        new ConSeqInfo(2, new VnLexiName[]{VnLexiName.vnl_k, VnLexiName.vnl_h, VnLexiName.vnl_nonVnChar}, false),
        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_l, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, false),
        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_m, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, true),
        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_n, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, true),
        new ConSeqInfo(2, new VnLexiName[]{VnLexiName.vnl_n, VnLexiName.vnl_g, VnLexiName.vnl_nonVnChar}, true),
        new ConSeqInfo(3, new VnLexiName[]{VnLexiName.vnl_n, VnLexiName.vnl_g, VnLexiName.vnl_h}, false),
        new ConSeqInfo(2, new VnLexiName[]{VnLexiName.vnl_n, VnLexiName.vnl_h, VnLexiName.vnl_nonVnChar}, true),
        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_p, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, true),
        new ConSeqInfo(2, new VnLexiName[]{VnLexiName.vnl_p, VnLexiName.vnl_h, VnLexiName.vnl_nonVnChar}, false),
        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_q, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, false),
        new ConSeqInfo(2, new VnLexiName[]{VnLexiName.vnl_q, VnLexiName.vnl_u, VnLexiName.vnl_nonVnChar}, false),
        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_r, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, false),
        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_s, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, false),
        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_t, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, true),
        new ConSeqInfo(2, new VnLexiName[]{VnLexiName.vnl_t, VnLexiName.vnl_h, VnLexiName.vnl_nonVnChar}, false),
        new ConSeqInfo(2, new VnLexiName[]{VnLexiName.vnl_t, VnLexiName.vnl_r, VnLexiName.vnl_nonVnChar}, false),
        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_v, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, false),
        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_x, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, false)
    };

    public int VSeqCount = VSeqList.length;

    public int CSeqCount = CSeqList.length;

    /**
     * Lưu chuỗi 3 nguyên âm vào trong mảng v[] rồi cho vào hàm lookupVSeq, trả
     * lại chuỗi nguyên âm vs (nếu có)
     */
    class VSeqPair {

        VnLexiName v[];
        VowelSeq vs;

        public VSeqPair() {
        }

        public VSeqPair(VnLexiName[] v, VowelSeq vs) {
            this.v = v;
            this.vs = vs;
        }
    };
    VSeqPair SortedVSeqList[] = new VSeqPair[VSeqCount];

    /**
     * Tương tự với VSeqPair
     */
    class CSeqPair {

        VnLexiName c[];
        ConSeq cs;

        public CSeqPair() {
        }

        public CSeqPair(VnLexiName[] c, ConSeq cs) {
            this.c = c;
            this.cs = cs;
        }
    };
    CSeqPair SortedCSeqList[] = new CSeqPair[CSeqCount];

    /**
     * Vowel-Consonant Pair
     */
    class VCPair {

        VowelSeq v;
        ConSeq c;

        public VCPair() {
        }

        public VCPair(VowelSeq v, ConSeq c) {
            this.v = v;
            this.c = c;
        }
    };

    VCPair VCPairList[] = {
        new VCPair(VowelSeq.vs_a, ConSeq.cs_c), new VCPair(VowelSeq.vs_a, ConSeq.cs_ch), new VCPair(VowelSeq.vs_a, ConSeq.cs_m), new VCPair(VowelSeq.vs_a, ConSeq.cs_n), new VCPair(VowelSeq.vs_a, ConSeq.cs_ng),
        new VCPair(VowelSeq.vs_a, ConSeq.cs_nh), new VCPair(VowelSeq.vs_a, ConSeq.cs_p), new VCPair(VowelSeq.vs_a, ConSeq.cs_t),
        new VCPair(VowelSeq.vs_ar, ConSeq.cs_c), new VCPair(VowelSeq.vs_ar, ConSeq.cs_m), new VCPair(VowelSeq.vs_ar, ConSeq.cs_n), new VCPair(VowelSeq.vs_ar, ConSeq.cs_ng), new VCPair(VowelSeq.vs_ar, ConSeq.cs_p), new VCPair(VowelSeq.vs_ar, ConSeq.cs_t),
        new VCPair(VowelSeq.vs_ab, ConSeq.cs_c), new VCPair(VowelSeq.vs_ab, ConSeq.cs_m), new VCPair(VowelSeq.vs_ab, ConSeq.cs_n), new VCPair(VowelSeq.vs_ab, ConSeq.cs_ng), new VCPair(VowelSeq.vs_ab, ConSeq.cs_p), new VCPair(VowelSeq.vs_ab, ConSeq.cs_t),
        new VCPair(VowelSeq.vs_e, ConSeq.cs_c), new VCPair(VowelSeq.vs_e, ConSeq.cs_ch), new VCPair(VowelSeq.vs_e, ConSeq.cs_m), new VCPair(VowelSeq.vs_e, ConSeq.cs_n), new VCPair(VowelSeq.vs_e, ConSeq.cs_ng),
        new VCPair(VowelSeq.vs_e, ConSeq.cs_nh), new VCPair(VowelSeq.vs_e, ConSeq.cs_p), new VCPair(VowelSeq.vs_e, ConSeq.cs_t),
        new VCPair(VowelSeq.vs_er, ConSeq.cs_c), new VCPair(VowelSeq.vs_er, ConSeq.cs_ch), new VCPair(VowelSeq.vs_er, ConSeq.cs_m), new VCPair(VowelSeq.vs_er, ConSeq.cs_n), new VCPair(VowelSeq.vs_er, ConSeq.cs_nh),
        new VCPair(VowelSeq.vs_er, ConSeq.cs_p), new VCPair(VowelSeq.vs_er, ConSeq.cs_t),
        new VCPair(VowelSeq.vs_i, ConSeq.cs_c), new VCPair(VowelSeq.vs_i, ConSeq.cs_ch), new VCPair(VowelSeq.vs_i, ConSeq.cs_m), new VCPair(VowelSeq.vs_i, ConSeq.cs_n), new VCPair(VowelSeq.vs_i, ConSeq.cs_nh), new VCPair(VowelSeq.vs_i, ConSeq.cs_p), new VCPair(VowelSeq.vs_i, ConSeq.cs_t),
        new VCPair(VowelSeq.vs_o, ConSeq.cs_c), new VCPair(VowelSeq.vs_o, ConSeq.cs_m), new VCPair(VowelSeq.vs_o, ConSeq.cs_n), new VCPair(VowelSeq.vs_o, ConSeq.cs_ng), new VCPair(VowelSeq.vs_o, ConSeq.cs_p), new VCPair(VowelSeq.vs_o, ConSeq.cs_t),
        new VCPair(VowelSeq.vs_or, ConSeq.cs_c), new VCPair(VowelSeq.vs_or, ConSeq.cs_m), new VCPair(VowelSeq.vs_or, ConSeq.cs_n), new VCPair(VowelSeq.vs_or, ConSeq.cs_ng), new VCPair(VowelSeq.vs_or, ConSeq.cs_p), new VCPair(VowelSeq.vs_or, ConSeq.cs_t),
        new VCPair(VowelSeq.vs_oh, ConSeq.cs_m), new VCPair(VowelSeq.vs_oh, ConSeq.cs_n), new VCPair(VowelSeq.vs_oh, ConSeq.cs_p), new VCPair(VowelSeq.vs_oh, ConSeq.cs_t),
        new VCPair(VowelSeq.vs_u, ConSeq.cs_c), new VCPair(VowelSeq.vs_u, ConSeq.cs_m), new VCPair(VowelSeq.vs_u, ConSeq.cs_n), new VCPair(VowelSeq.vs_u, ConSeq.cs_ng), new VCPair(VowelSeq.vs_u, ConSeq.cs_p), new VCPair(VowelSeq.vs_u, ConSeq.cs_t),
        new VCPair(VowelSeq.vs_uh, ConSeq.cs_c), new VCPair(VowelSeq.vs_uh, ConSeq.cs_m), new VCPair(VowelSeq.vs_uh, ConSeq.cs_n), new VCPair(VowelSeq.vs_uh, ConSeq.cs_ng), new VCPair(VowelSeq.vs_uh, ConSeq.cs_t),
        new VCPair(VowelSeq.vs_y, ConSeq.cs_t),
        new VCPair(VowelSeq.vs_ie, ConSeq.cs_c), new VCPair(VowelSeq.vs_ie, ConSeq.cs_m), new VCPair(VowelSeq.vs_ie, ConSeq.cs_n), new VCPair(VowelSeq.vs_ie, ConSeq.cs_ng), new VCPair(VowelSeq.vs_ie, ConSeq.cs_p), new VCPair(VowelSeq.vs_ie, ConSeq.cs_t),
        new VCPair(VowelSeq.vs_ier, ConSeq.cs_c), new VCPair(VowelSeq.vs_ier, ConSeq.cs_m), new VCPair(VowelSeq.vs_ier, ConSeq.cs_n), new VCPair(VowelSeq.vs_ier, ConSeq.cs_ng), new VCPair(VowelSeq.vs_ier, ConSeq.cs_p), new VCPair(VowelSeq.vs_ier, ConSeq.cs_t),
        new VCPair(VowelSeq.vs_oa, ConSeq.cs_c), new VCPair(VowelSeq.vs_oa, ConSeq.cs_ch), new VCPair(VowelSeq.vs_oa, ConSeq.cs_m), new VCPair(VowelSeq.vs_oa, ConSeq.cs_n), new VCPair(VowelSeq.vs_oa, ConSeq.cs_ng),
        new VCPair(VowelSeq.vs_oa, ConSeq.cs_nh), new VCPair(VowelSeq.vs_oa, ConSeq.cs_p), new VCPair(VowelSeq.vs_oa, ConSeq.cs_t),
        new VCPair(VowelSeq.vs_oab, ConSeq.cs_c), new VCPair(VowelSeq.vs_oab, ConSeq.cs_m), new VCPair(VowelSeq.vs_oab, ConSeq.cs_n), new VCPair(VowelSeq.vs_oab, ConSeq.cs_ng), new VCPair(VowelSeq.vs_oab, ConSeq.cs_t),
        new VCPair(VowelSeq.vs_oe, ConSeq.cs_n), new VCPair(VowelSeq.vs_oe, ConSeq.cs_t),
        new VCPair(VowelSeq.vs_ua, ConSeq.cs_n), new VCPair(VowelSeq.vs_ua, ConSeq.cs_ng), new VCPair(VowelSeq.vs_ua, ConSeq.cs_t),
        new VCPair(VowelSeq.vs_uar, ConSeq.cs_n), new VCPair(VowelSeq.vs_uar, ConSeq.cs_ng), new VCPair(VowelSeq.vs_uar, ConSeq.cs_t),
        new VCPair(VowelSeq.vs_ue, ConSeq.cs_c), new VCPair(VowelSeq.vs_ue, ConSeq.cs_ch), new VCPair(VowelSeq.vs_ue, ConSeq.cs_n), new VCPair(VowelSeq.vs_ue, ConSeq.cs_nh),
        new VCPair(VowelSeq.vs_uer, ConSeq.cs_c), new VCPair(VowelSeq.vs_uer, ConSeq.cs_ch), new VCPair(VowelSeq.vs_uer, ConSeq.cs_n), new VCPair(VowelSeq.vs_uer, ConSeq.cs_nh),
        new VCPair(VowelSeq.vs_uo, ConSeq.cs_c), new VCPair(VowelSeq.vs_uo, ConSeq.cs_m), new VCPair(VowelSeq.vs_uo, ConSeq.cs_n), new VCPair(VowelSeq.vs_uo, ConSeq.cs_ng), new VCPair(VowelSeq.vs_uo, ConSeq.cs_p), new VCPair(VowelSeq.vs_uo, ConSeq.cs_t),
        new VCPair(VowelSeq.vs_uor, ConSeq.cs_c), new VCPair(VowelSeq.vs_uor, ConSeq.cs_m), new VCPair(VowelSeq.vs_uor, ConSeq.cs_n), new VCPair(VowelSeq.vs_uor, ConSeq.cs_ng), new VCPair(VowelSeq.vs_uor, ConSeq.cs_t),
        new VCPair(VowelSeq.vs_uho, ConSeq.cs_c), new VCPair(VowelSeq.vs_uho, ConSeq.cs_m), new VCPair(VowelSeq.vs_uho, ConSeq.cs_n), new VCPair(VowelSeq.vs_uho, ConSeq.cs_ng), new VCPair(VowelSeq.vs_uho, ConSeq.cs_p), new VCPair(VowelSeq.vs_uho, ConSeq.cs_t),
        new VCPair(VowelSeq.vs_uhoh, ConSeq.cs_c), new VCPair(VowelSeq.vs_uhoh, ConSeq.cs_m), new VCPair(VowelSeq.vs_uhoh, ConSeq.cs_n), new VCPair(VowelSeq.vs_uhoh, ConSeq.cs_ng), new VCPair(VowelSeq.vs_uhoh, ConSeq.cs_p), new VCPair(VowelSeq.vs_uhoh, ConSeq.cs_t),
        new VCPair(VowelSeq.vs_uy, ConSeq.cs_c), new VCPair(VowelSeq.vs_uy, ConSeq.cs_ch), new VCPair(VowelSeq.vs_uy, ConSeq.cs_n), new VCPair(VowelSeq.vs_uy, ConSeq.cs_nh), new VCPair(VowelSeq.vs_uy, ConSeq.cs_p), new VCPair(VowelSeq.vs_uy, ConSeq.cs_t),
        new VCPair(VowelSeq.vs_ye, ConSeq.cs_m), new VCPair(VowelSeq.vs_ye, ConSeq.cs_n), new VCPair(VowelSeq.vs_ye, ConSeq.cs_ng), new VCPair(VowelSeq.vs_ye, ConSeq.cs_p), new VCPair(VowelSeq.vs_ye, ConSeq.cs_t),
        new VCPair(VowelSeq.vs_yer, ConSeq.cs_m), new VCPair(VowelSeq.vs_yer, ConSeq.cs_n), new VCPair(VowelSeq.vs_yer, ConSeq.cs_ng), new VCPair(VowelSeq.vs_yer, ConSeq.cs_t),
        new VCPair(VowelSeq.vs_uye, ConSeq.cs_n), new VCPair(VowelSeq.vs_uye, ConSeq.cs_t),
        new VCPair(VowelSeq.vs_uyer, ConSeq.cs_n), new VCPair(VowelSeq.vs_uyer, ConSeq.cs_t)

    };
    public int VCPairCount = VCPairList.length;

    //------------------------------------------------
    public VowelSeq lookupVSeq(VnLexiName v1, VnLexiName v2, VnLexiName v3) {
        VSeqPair key = new VSeqPair();
        key.v[0] = v1;
        key.v[1] = v2;
        key.v[2] = v3;

//        VSeqPair *pInfo = (VSeqPair *)bsearch(&key, SortedVSeqList, VSeqCount, sizeof(VSeqPair), tripleVowelCompare);
//        if (pInfo == 0)
//            return vs_nil;
//        return pInfo->vs;
        return key.vs;
    }

//------------------------------------------------
    public ConSeq lookupCSeq(VnLexiName c1, VnLexiName c2, VnLexiName c3) {
        CSeqPair key = new CSeqPair();
        key.c[0] = c1;
        key.c[1] = c2;
        key.c[2] = c3;

//        CSeqPair *pInfo = (CSeqPair *)bsearch(&key, SortedCSeqList, CSeqCount, sizeof(CSeqPair), tripleConCompare);
//        if (pInfo == 0)
//            return cs_nil;
//        return pInfo->cs;
        return key.cs;
    }

//------------------------------------------------
    int tripleVowelCompare(VSeqPair p1, VSeqPair p2) {
        VSeqPair t1 = p1;
        VSeqPair t2 = p2;

//        for (int i=0; i<3; i++) {
//            if (t1.v[i] < t2.v[i])
//                return -1;
//            if (t1.v[i] > t2.v[i])
//                return 1;
//        }
        return 0;
    }

//------------------------------------------------
    int tripleConCompare(CSeqPair p1, CSeqPair p2) {
        CSeqPair t1 = p1;
        CSeqPair t2 = p2;

//        for (int i=0; i<3; i++) {
//            if (t1.c[i] < t2.c[i])
//                return -1;
//            if (t1.c[i] > t2.c[i])
//                return 1;
//        }
        return 0;
    }

//------------------------------------------------
    int VCPairCompare(VCPair p1, VCPair p2) {
        VCPair t1 = p1;
        VCPair t2 = p2;

//        if (t1.v < t2.v)
//            return -1;
//        if (t1.v > t2.v)
//          return 1;
//
//        if (t1.c < t2.c)
//            return -1;
//        if (t1.c > t2.c)
//            return 1;
        return 0;
    }

//----------------------------------------------------------
    boolean isValidCV(ConSeq c, VowelSeq v) {
        if (c == ConSeq.cs_nil || v == VowelSeq.vs_nil) {
            return true;
        }

        VowelSeqInfo vInfo = new VowelSeqInfo(); //= VSeqList[v];

        if ((c == ConSeq.cs_gi && vInfo.v[0] == VnLexiName.vnl_i)
                || (c == ConSeq.cs_qu && vInfo.v[0] == VnLexiName.vnl_u)) {
            return false; // gi doesn't go with i, qu doesn't go with u
        }
        if (c == ConSeq.cs_k) {
            // k can only go with the following vowel sequences
            VowelSeq kVseq[] = {VowelSeq.vs_e, VowelSeq.vs_i, VowelSeq.vs_y, VowelSeq.vs_er, VowelSeq.vs_eo, VowelSeq.vs_eu, VowelSeq.vs_eru, VowelSeq.vs_ia, VowelSeq.vs_ie, VowelSeq.vs_ier, VowelSeq.vs_ieu, VowelSeq.vs_ieru, VowelSeq.vs_nil};
            int i;
            for (i = 0; kVseq[i] != VowelSeq.vs_nil && kVseq[i] != v; i++);
            return (kVseq[i] != VowelSeq.vs_nil);
        }

        //More checks
        return true;
    }

//----------------------------------------------------------
    boolean isValidVC(VowelSeq v, ConSeq c) {
        if (v == VowelSeq.vs_nil || c == ConSeq.cs_nil) {
            return true;
        }

        VowelSeqInfo vInfo = new VowelSeqInfo(); //VSeqList[v];
        if (!(vInfo.conSuffix == 1)) {
            return false;
        }

        ConSeqInfo cInfo = new ConSeqInfo();//CSeqList[c];
        if (!cInfo.suffix) {
            return false;
        }

        VCPair p = new VCPair();
        p.v = v;
        p.c = c;
//        if (bsearch(&p, VCPairList, VCPairCount, sizeof(VCPair), VCPairCompare))
//            return true;

        return false;
    }

//----------------------------------------------------------
    boolean isValidCVC(ConSeq c1, VowelSeq v, ConSeq c2) {
        if (v == VowelSeq.vs_nil) {
            return (c1 == ConSeq.cs_nil || c2 != ConSeq.cs_nil);
        }

        if (c1 == ConSeq.cs_nil) {
            return isValidVC(v, c2);
        }

        if (c2 == ConSeq.cs_nil) {
            return isValidCV(c1, v);
        }

        boolean okCV = isValidCV(c1, v);
        boolean okVC = isValidVC(v, c2);

        if (okCV && okVC) {
            return true;
        }

        if (!okVC) {
            //check some exceptions: vc fails but cvc passes

            // quyn, quynh
            if (c1 == ConSeq.cs_qu && v == VowelSeq.vs_y && (c2 == ConSeq.cs_n || c2 == ConSeq.cs_nh)) {
                return true;
            }

            // gieng, gie^ng
            if (c1 == ConSeq.cs_gi && (v == VowelSeq.vs_e || v == VowelSeq.vs_er) && (c2 == ConSeq.cs_n || c2 == ConSeq.cs_ng)) {
                return true;
            }
        }
        return false;
    }

//    public int processRoof(UkKeyEvent & ev)
//{
//    if (!m_pCtrl->vietKey || m_current < 0 || m_buffer[m_current].vOffset < 0)
//        return processAppend(ev);
//
//    VnLexiName target;
//    switch (ev.evType) {
//    case vneRoof_a:
//        target = vnl_ar;
//        break;
//    case vneRoof_e:
//        target = vnl_er;
//        break;
//    case vneRoof_o:
//        target = vnl_or;
//        break;
//    default:
//        target = vnl_nonVnChar;
//    }
//
//
//    VowelSeq vs, newVs;
//    int i, vStart, vEnd;
//    int curTonePos, newTonePos, tone;
//    int changePos;
//    bool roofRemoved = false;
//
//    vEnd = m_current - m_buffer[m_current].vOffset;
//    vs = m_buffer[vEnd].vseq;
//    vStart = vEnd - (VSeqList[vs].len - 1);
//    curTonePos = vStart + getTonePosition(vs, vEnd == m_current);
//    tone = m_buffer[curTonePos].tone;
//
//    bool doubleChangeUO = false;
//    if (vs == vs_uho || vs == vs_uhoh || vs == vs_uhoi || vs == vs_uhohi) {
//        //special cases: u+o+ -> uo^, u+o -> uo^, u+o+i -> uo^i, u+oi -> uo^i
//        newVs = lookupVSeq(vnl_u, vnl_or, VSeqList[vs].v[2]);
//        doubleChangeUO = true;
//    }
//    else {
//        newVs = VSeqList[vs].withRoof;
//    }
//
//    VowelSeqInfo *pInfo;
//
//    if (newVs == vs_nil) {
//        if (VSeqList[vs].roofPos == -1)
//            return processAppend(ev); //roof is not applicable
//    
//        //a roof already exists -> undo roof
//        VnLexiName curCh = m_buffer[vStart + VSeqList[vs].roofPos].vnSym;
//        if (target != vnl_nonVnChar && curCh != target)
//            return processAppend(ev); //specific roof and the roof character don't match
//
//        VnLexiName newCh = (curCh == vnl_ar)? vnl_a : ((curCh == vnl_er)? vnl_e : vnl_o);
//        changePos = vStart + VSeqList[vs].roofPos;
//
//        if (!m_pCtrl->options.freeMarking && changePos != m_current)
//            return processAppend(ev);
//
//        markChange(changePos);
//        m_buffer[changePos].vnSym = newCh;
//
//        if (VSeqList[vs].len == 3)
//            newVs = lookupVSeq(m_buffer[vStart].vnSym, m_buffer[vStart+1].vnSym, m_buffer[vStart+2].vnSym);
//        else if (VSeqList[vs].len == 2)
//            newVs = lookupVSeq(m_buffer[vStart].vnSym, m_buffer[vStart+1].vnSym);
//        else
//            newVs = lookupVSeq(m_buffer[vStart].vnSym);
//
//        pInfo = &VSeqList[newVs];
//        roofRemoved = true;
//    }
//    else {
//        pInfo = &VSeqList[newVs];
//        if (target != vnl_nonVnChar &&  pInfo->v[pInfo->roofPos] != target)
//            return processAppend(ev);
//
//        //check validity of new VC and CV
//        bool valid = true;
//        ConSeq c1 = cs_nil;
//        ConSeq c2 = cs_nil;
//        if (m_buffer[m_current].c1Offset != -1)
//            c1 = m_buffer[m_current-m_buffer[m_current].c1Offset].cseq;
//        
//        if (m_buffer[m_current].c2Offset != -1)
//            c2 = m_buffer[m_current-m_buffer[m_current].c2Offset].cseq;
//
//        valid = isValidCVC(c1, newVs, c2);
//        if (!valid)
//            return processAppend(ev);
//
//        if (doubleChangeUO) {
//            changePos = vStart;
//        }
//        else {
//            changePos = vStart + pInfo->roofPos;
//        }
//        if (!m_pCtrl->options.freeMarking && changePos != m_current)
//            return processAppend(ev);
//        markChange(changePos);
//        if (doubleChangeUO) {
//            m_buffer[vStart].vnSym = vnl_u;
//            m_buffer[vStart+1].vnSym = vnl_or;
//        }
//        else {
//            m_buffer[changePos].vnSym = pInfo->v[pInfo->roofPos];
//        }
//    }
//
//    for (i=0; i < pInfo->len; i++) { //update sub-sequences
//        m_buffer[vStart+i].vseq = pInfo->sub[i];
//    }
//
//    //check if tone re-position is needed
//    newTonePos = vStart + getTonePosition(newVs, vEnd == m_current);
//    /* //For now, users don't seem to like the following processing, thus commented out
//    if (roofRemoved && tone != 0 &&
//        (!pInfo->complete || changePos == curTonePos)) {
//        //remove tone if the vowel sequence becomes incomplete as a result of roof removal OR
//        //if removed roof is at the same position as the current tone
//        markChange(curTonePos);
//        m_buffer[curTonePos].tone = 0;
//    } else
//    */
//    if (curTonePos != newTonePos && tone != 0) {
//        markChange(newTonePos);
//        m_buffer[newTonePos].tone = tone;
//        markChange(curTonePos);
//        m_buffer[curTonePos].tone = 0;
//    }
//
//    if (roofRemoved) {
//        m_singleMode = false;
//        processAppend(ev);
//        m_reverted = true;
//    }
//
//    return 1;
//}
//------------------------------------------------------------------
// can only be called from processHook
//------------------------------------------------------------------
int processHookWithUO(UkKeyEvent & ev)
{
    VowelSeq vs, newVs;
    int i, vStart, vEnd;
    int curTonePos, newTonePos, tone;
    boolean hookRemoved = false;
    boolean removeWithUndo = true;
    boolean toneRemoved = false;
    
    (void)toneRemoved; // fix warning
    
    VnLexiName v;

    if (!m_pCtrl->options.freeMarking && m_buffer[m_current].vOffset != 0)
        return processAppend(ev);    

    vEnd = m_current - m_buffer[m_current].vOffset;
    vs = m_buffer[vEnd].vseq;
    vStart = vEnd - (VSeqList[vs].len - 1);
    v = VSeqList[vs].v;
    curTonePos = vStart + getTonePosition(vs, vEnd == m_current);
    tone = m_buffer[curTonePos].tone;

    switch (ev.evType) {
    case vneHook_u:
        if (v[0] == vnl_u) {
            newVs = VSeqList[vs].withHook;
            markChange(vStart);
            m_buffer[vStart].vnSym = vnl_uh;
        }
        else {// v[0] = vnl_uh, -> uo
            newVs = lookupVSeq(vnl_u, vnl_o, v[2]);
            markChange(vStart);
            m_buffer[vStart].vnSym = vnl_u;
            m_buffer[vStart+1].vnSym = vnl_o;
            hookRemoved = true;
            toneRemoved =  (m_buffer[vStart].tone != 0);
        }
        break;
    case vneHook_o:
        if (v[1] == vnl_o || v[1] == vnl_or) {
            if (vEnd == m_current && VSeqList[vs].len == 2 && 
                m_buffer[m_current].form == vnw_cv && m_buffer[m_current-2].cseq == cs_th)
            {
                // o|o^ -> o+
                newVs = VSeqList[vs].withHook;
                markChange(vStart+1);
                m_buffer[vStart+1].vnSym = vnl_oh;
            }
            else {
                newVs = lookupVSeq(vnl_uh, vnl_oh, v[2]);
                if (v[0] == vnl_u) {
                    markChange(vStart);
                    m_buffer[vStart].vnSym = vnl_uh;
                    m_buffer[vStart+1].vnSym = vnl_oh;
                }
                else {
                    markChange(vStart+1);
                    m_buffer[vStart+1].vnSym = vnl_oh;
                }
            }
        }
        else {// v[1] = vnl_oh, -> uo
            newVs = lookupVSeq(vnl_u, vnl_o, v[2]);
            if (v[0] == vnl_uh) {
                markChange(vStart);
                m_buffer[vStart].vnSym = vnl_u;
                m_buffer[vStart+1].vnSym = vnl_o;
            }
            else {
                markChange(vStart+1);
                m_buffer[vStart+1].vnSym = vnl_o;
            }
            hookRemoved = true;
            toneRemoved = (m_buffer[vStart+1].tone != 0);
        }
        break;
    default:  //vneHookAll, vneHookUO:
        if (v[0] == vnl_u) {
            if (v[1] == vnl_o || v[1] == vnl_or) { 
                //uo -> uo+ if prefixed by "th"
                if ((vs == vs_uo || vs == vs_uor) && vEnd == m_current && 
                    m_buffer[m_current].form == vnw_cv && m_buffer[m_current-2].cseq == cs_th) 
                {
                    newVs = vs_uoh;
                    markChange(vStart+1);
                    m_buffer[vStart+1].vnSym = vnl_oh;
                }
                else {
                    //uo -> u+o+
                    newVs = VSeqList[vs].withHook;
                    markChange(vStart);
                    m_buffer[vStart].vnSym = vnl_uh;
                    newVs = VSeqList[newVs].withHook;
                    m_buffer[vStart+1].vnSym = vnl_oh;
                }
            }
            else {//uo+ -> u+o+
                newVs = VSeqList[vs].withHook;
                markChange(vStart);
                m_buffer[vStart].vnSym = vnl_uh;
            }
        }
        else {//v[0] == vnl_uh
            if (v[1] == vnl_o) { // u+o -> u+o+
                newVs = VSeqList[vs].withHook;
                markChange(vStart+1);
                m_buffer[vStart+1].vnSym = vnl_oh;
            }
            else { //v[1] == vnl_oh, u+o+ -> uo
                newVs = lookupVSeq(vnl_u, vnl_o, v[2]); //vs_uo;
                markChange(vStart);
                m_buffer[vStart].vnSym = vnl_u;
                m_buffer[vStart+1].vnSym = vnl_o;
                hookRemoved = true;
                toneRemoved = (m_buffer[vStart].tone != 0 || m_buffer[vStart+1].tone != 0);
            }
        }
        break;
    }

    VowelSeqInfo *p = &VSeqList[newVs];
    for (i=0; i < p->len; i++) { //update sub-sequences
        m_buffer[vStart+i].vseq = p->sub[i];
    }

    //check if tone re-position is needed
    newTonePos = vStart + getTonePosition(newVs, vEnd == m_current);
    /* //For now, users don't seem to like the following processing, thus commented out
    if (hookRemoved && tone != 0 && (!p->complete || toneRemoved)) {
        //remove tone if the vowel sequence becomes incomplete as a result of hook removal
        //OR if a removed hook is at the same position as the current tone
        markChange(curTonePos);
        m_buffer[curTonePos].tone = 0;
    }
    else 
    */
    if (curTonePos != newTonePos && tone != 0) {
        markChange(newTonePos);
        m_buffer[newTonePos].tone = tone;
        markChange(curTonePos);
        m_buffer[curTonePos].tone = 0;
    }

    if (hookRemoved && removeWithUndo) {
        m_singleMode = false;
        processAppend(ev);
        m_reverted = true;
    }

    return 1;
}
------------------------------------------------------------------
int processHook(UkKeyEvent & ev)
{
    if (!m_pCtrl->vietKey || m_current < 0 || m_buffer[m_current].vOffset < 0)
        return processAppend(ev);

    VowelSeq vs, newVs;
    int i, vStart, vEnd;
    int curTonePos, newTonePos, tone;
    int changePos;
    bool hookRemoved = false;
    VowelSeqInfo *pInfo;
    VnLexiName *v;

    vEnd = m_current - m_buffer[m_current].vOffset;
    vs = m_buffer[vEnd].vseq;

    v = VSeqList[vs].v;
  
    if (VSeqList[vs].len > 1 && 
        ev.evType != vneBowl &&
        (v[0] == vnl_u || v[0] == vnl_uh) &&
        (v[1] == vnl_o || v[1] == vnl_oh || v[1] == vnl_or))
        return processHookWithUO(ev);

    vStart = vEnd - (VSeqList[vs].len - 1);
    curTonePos = vStart + getTonePosition(vs, vEnd == m_current);
    tone = m_buffer[curTonePos].tone;

    newVs = VSeqList[vs].withHook;
    if (newVs == vs_nil) {
        if (VSeqList[vs].hookPos == -1)
            return processAppend(ev); //hook is not applicable

        //a hook already exists -> undo hook
        VnLexiName curCh = m_buffer[vStart + VSeqList[vs].hookPos].vnSym;
        VnLexiName newCh = (curCh == vnl_ab)? vnl_a : ((curCh == vnl_uh)? vnl_u : vnl_o);
        changePos = vStart + VSeqList[vs].hookPos;
        if (!m_pCtrl->options.freeMarking && changePos != m_current)
            return processAppend(ev);

        switch (ev.evType) {
        case vneHook_u:
            if (curCh != vnl_uh)
                return processAppend(ev);
            break;
        case vneHook_o:
            if (curCh != vnl_oh)
                return processAppend(ev);
            break;
        case vneBowl:
            if (curCh != vnl_ab)
                return processAppend(ev);
            break;
        default:
            if (ev.evType == vneHook_uo && curCh == vnl_ab)
                return processAppend(ev);
        }

        markChange(changePos);
        m_buffer[changePos].vnSym = newCh;

        if (VSeqList[vs].len == 3)
            newVs = lookupVSeq(m_buffer[vStart].vnSym, m_buffer[vStart+1].vnSym, m_buffer[vStart+2].vnSym);
        else if (VSeqList[vs].len == 2)
            newVs = lookupVSeq(m_buffer[vStart].vnSym, m_buffer[vStart+1].vnSym);
        else
            newVs = lookupVSeq(m_buffer[vStart].vnSym);

        pInfo = &VSeqList[newVs];
        hookRemoved = true;
    }
    else {
        pInfo = &VSeqList[newVs];

        switch (ev.evType) {
        case vneHook_u:
            if (pInfo->v[pInfo->hookPos] != vnl_uh)
                return processAppend(ev);
            break;
        case vneHook_o:
            if (pInfo->v[pInfo->hookPos] != vnl_oh)
                return processAppend(ev);
            break;
        case vneBowl:
            if (pInfo->v[pInfo->hookPos] != vnl_ab)
                return processAppend(ev);
            break;
        default: //vneHook_uo, vneHookAll
            if (ev.evType == vneHook_uo && pInfo->v[pInfo->hookPos] == vnl_ab)
                return processAppend(ev);
        }

        //check validity of new VC and CV
        bool valid = true;
        ConSeq c1 = cs_nil;
        ConSeq c2 = cs_nil;
        if (m_buffer[m_current].c1Offset != -1)
            c1 = m_buffer[m_current-m_buffer[m_current].c1Offset].cseq;
        
        if (m_buffer[m_current].c2Offset != -1)
            c2 = m_buffer[m_current-m_buffer[m_current].c2Offset].cseq;

        valid = isValidCVC(c1, newVs, c2);

        if (!valid)
            return processAppend(ev);

        changePos = vStart + pInfo->hookPos;
        if (!m_pCtrl->options.freeMarking && changePos != m_current)
            return processAppend(ev);

        markChange(changePos);
        m_buffer[changePos].vnSym = pInfo->v[pInfo->hookPos];
    }
   
    for (i=0; i < pInfo->len; i++) { //update sub-sequences
        m_buffer[vStart+i].vseq = pInfo->sub[i];
    }

    //check if tone re-position is needed
    newTonePos = vStart + getTonePosition(newVs, vEnd == m_current);
    /* //For now, users don't seem to like the following processing, thus commented out
    if (hookRemoved && tone != 0 && 
        (!pInfo->complete || (hookRemoved && curTonePos == changePos))) {
        //remove tone if the vowel sequence becomes incomplete as a result of hook removal
        //OR if a removed hook was at the same position as the current tone
        markChange(curTonePos);
        m_buffer[curTonePos].tone = 0;
    }
    else */
    if (curTonePos != newTonePos && tone != 0) {
        markChange(newTonePos);
        m_buffer[newTonePos].tone = tone;
        markChange(curTonePos);
        m_buffer[curTonePos].tone = 0;
    }

    if (hookRemoved) {
        m_singleMode = false;
        processAppend(ev);
        m_reverted = true;
    }

    return 1;
}
//----------------------------------------------------------
int getTonePosition(VowelSeq vs, boolean terminated)
{
    VowelSeqInfo info = VSeqList[vs];
    if (info.len == 1)
        return 0;

    if (info.roofPos != -1)
        return info.roofPos;
    if (info.hookPos != -1) {
        if (vs == vs_uhoh || vs == vs_uhohi || vs == vs_uhohu) //u+o+, u+o+u, u+o+i
            return 1;
        return info.hookPos;
    }
  
    if (info.len == 3)
        return 1;

    if (m_pCtrl->options.modernStyle &&
        (vs == vs_oa || vs == vs_oe ||vs == vs_uy))
        return 1;

    return terminated ? 0 : 1;
}
//----------------------------------------------------------
int UkEngine::processTone(UkKeyEvent & ev)
{
    if (m_current < 0 || !m_pCtrl->vietKey)
        return processAppend(ev);

    if (m_buffer[m_current].form == vnw_c && 
        (m_buffer[m_current].cseq == cs_gi || m_buffer[m_current].cseq == cs_gin)) {
        int p = (m_buffer[m_current].cseq == cs_gi)? m_current : m_current - 1;
        if (m_buffer[p].tone == 0 && ev.tone == 0)
            return processAppend(ev);
        markChange(p);
        if (m_buffer[p].tone == ev.tone) {
            m_buffer[p].tone = 0;
            m_singleMode = false;
            processAppend(ev);
            m_reverted = true;
            return 1;
        }
        m_buffer[p].tone = ev.tone;
        return 1;
    }

    if (m_buffer[m_current].vOffset < 0)
        return processAppend(ev);

    int vEnd;
    VowelSeq vs;

    vEnd = m_current - m_buffer[m_current].vOffset;
    vs = m_buffer[vEnd].vseq;
    VowelSeqInfo & info = VSeqList[vs];
    if (m_pCtrl->options.spellCheckEnabled && !m_pCtrl->options.freeMarking && !info.complete)
        return processAppend(ev);

    if (m_buffer[m_current].form == vnw_vc || m_buffer[m_current].form == vnw_cvc) {
        ConSeq cs = m_buffer[m_current].cseq;
        if ((cs == cs_c || cs == cs_ch || cs == cs_p || cs == cs_t) &&
            (ev.tone == 2 || ev.tone == 3 || ev.tone == 4))
            return processAppend(ev); // c, ch, p, t suffixes don't allow ` ? ~
    }
      
    int toneOffset = getTonePosition(vs, vEnd == m_current);
    int tonePos = vEnd - (info.len -1 ) + toneOffset;
    if (m_buffer[tonePos].tone == 0 && ev.tone == 0)
        return processAppend(ev);

    if (m_buffer[tonePos].tone == ev.tone) {
        markChange(tonePos);
        m_buffer[tonePos].tone = 0;
        m_singleMode = false;
        processAppend(ev);
        m_reverted = true;
        return 1;
    }

    markChange(tonePos);
    m_buffer[tonePos].tone = ev.tone;
    return 1;
}
//
////----------------------------------------------------------
int UkEngine::processDd(UkKeyEvent & ev)
{
    if (!m_pCtrl->vietKey || m_current < 0)
        return processAppend(ev);
    
    int pos;

    // we want to allow dd even in non-vn sequence, because dd is used a lot in abbreviation
    // we allow dd only if preceding character is not a vowel
    if (m_buffer[m_current].form == vnw_nonVn && 
        m_buffer[m_current].vnSym == vnl_d &&
        (m_buffer[m_current-1].vnSym == vnl_nonVnChar ||!IsVnVowel[m_buffer[m_current-1].vnSym]))
    {
        m_singleMode = true;
        pos = m_current;
        markChange(pos);
        m_buffer[pos].cseq = cs_dd;
        m_buffer[pos].vnSym = vnl_dd;
        m_buffer[pos].form = vnw_c;
        m_buffer[pos].c1Offset = 0;
        m_buffer[pos].c2Offset = -1;
        m_buffer[pos].vOffset = -1;
        return 1;
    }

    if (m_buffer[m_current].c1Offset < 0) {
        return processAppend(ev);
    }

    pos = m_current - m_buffer[m_current].c1Offset;
    if (!m_pCtrl->options.freeMarking && pos != m_current)
        return processAppend(ev);

    if (m_buffer[pos].cseq == cs_d) {
        markChange(pos);
        m_buffer[pos].cseq = cs_dd;
        m_buffer[pos].vnSym = vnl_dd;
        //never spellcheck a word which starts with dd, because it's used alot in abbreviation
        m_singleMode = true;
        return 1;
    }

    if (m_buffer[pos].cseq == cs_dd) {
        //undo dd
        markChange(pos);
        m_buffer[pos].cseq = cs_d;
        m_buffer[pos].vnSym = vnl_d;
        m_singleMode = false;
        processAppend(ev);
        m_reverted = true;
        return 1;
    }
  
    return processAppend(ev);
}
    public VowelSeqInfo[] getVSeqList() {
        return VSeqList;
    }

    public void setVSeqList(VowelSeqInfo[] VSeqList) {
        this.VSeqList = VSeqList;
    }

    public ConSeqInfo[] getCSeqList() {
        return CSeqList;
    }

    public void setCSeqList(ConSeqInfo[] CSeqList) {
        this.CSeqList = CSeqList;
    }

    public int getVSeqCount() {
        return VSeqCount;
    }

    public void setVSeqCount(int VSeqCount) {
        this.VSeqCount = VSeqCount;
    }

    public VSeqPair[] getSortedVSeqList() {
        return SortedVSeqList;
    }

    public void setSortedVSeqList(VSeqPair[] SortedVSeqList) {
        this.SortedVSeqList = SortedVSeqList;
    }

    public int getCSeqCount() {
        return CSeqCount;
    }

    public void setCSeqCount(int CSeqCount) {
        this.CSeqCount = CSeqCount;
    }

    public CSeqPair[] getSortedCSeqList() {
        return SortedCSeqList;
    }

    public void setSortedCSeqList(CSeqPair[] SortedCSeqList) {
        this.SortedCSeqList = SortedCSeqList;
    }

    public VCPair[] getVCPairList() {
        return VCPairList;
    }

    public void setVCPairList(VCPair[] VCPairList) {
        this.VCPairList = VCPairList;
    }

}

//    @Override
//    public boolean checkInvalidate(String target) {
//
//        /*
//         * a letter in word is not contained in this string
//         */
//        String invalidLetters = "0123456789fjzw!@#$~";
//
//        /*
//         * Split a sentence to words
//         */
//        String[] target_array = target.split(" ");
//
//        for (String target_word : target_array) {
//
//            // each letter in the target word is a string, need to be improved in the future
//            String[] letters = target_word.split("");
//            for (String letter : letters) {
//                if (invalidLetters.contains(letter)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

