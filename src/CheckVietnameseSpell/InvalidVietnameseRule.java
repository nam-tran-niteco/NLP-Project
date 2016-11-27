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
    public class VowelSeqInfo {

        int len; // độ dài chuỗi nguyên âm (tối đa là 3)
        int complete; // chưa hiểu cái này lắm vì có cái chuỗi nguyên âm không tồn tại mà complete vẫn bằng 1
        int conSuffix; //allow consonnant suffix
        public int vSeq[]; // những kí tự tạo thành chuỗi nguyên âm
        int sub[]; // chưa hiểu chuỗi này đại diện cho cái gì

        int roofPos;
        int vSeqWithRoof;

        int hookPos;
        int vSeqWithHook; //hook & bowl
        
        public VowelSeqInfo() {
        }

        public VowelSeqInfo(int len, int complete, int conSuffix, int[] vSeq, int[] sub, int roofPos, int vSeqWithRoof, int hookPos, int vSeqWithHook) {
            this.len = len;
            this.complete = complete;
            this.conSuffix = conSuffix;
            this.vSeq = vSeq;
            this.sub = sub;
            this.roofPos = roofPos;
            this.vSeqWithRoof = vSeqWithRoof;
            this.hookPos = hookPos;
            this.vSeqWithHook = vSeqWithHook;
        }
        
    }
    
    VowelSeqInfo VSeqList[] = {
            new VowelSeqInfo(1, 1, 1, new int[]{1, 0, 0}, new int[]{1, 0, 0}, -1, 2, -1, 3),
            new VowelSeqInfo(1, 1, 1, new int[]{7, 0, 0}, new int[]{2, 0, 0}, 0, 0, -1, 3),
            new VowelSeqInfo(1, 1, 1, new int[]{13, 0, 0}, new int[]{3, 0, 0}, -1, 2, 0, 0),
            new VowelSeqInfo(1, 1, 1, new int[]{23, 0, 0}, new int[]{4, 0, 0}, -1, 5, -1, 0),
            new VowelSeqInfo(1, 1, 1, new int[]{29, 0, 0}, new int[]{5, 0, 0}, 0, 0, -1, 0),
            new VowelSeqInfo(1, 1, 1, new int[]{37, 0, 0}, new int[]{6, 0, 0}, -1, 0, -1, 0),
            new VowelSeqInfo(1, 1, 1, new int[]{47, 0, 0}, new int[]{7, 0, 0}, -1, 8, -1, 9),
            new VowelSeqInfo(1, 1, 1, new int[]{53, 0, 0}, new int[]{8, 0, 0}, 0, 0, -1, 9),
            new VowelSeqInfo(1, 1, 1, new int[]{59, 0, 0}, new int[]{9, 0, 0}, -1, 8, 0, 0),
            new VowelSeqInfo(1, 1, 1, new int[]{70, 0, 0}, new int[]{10, 0, 0}, -1, 0, -1, 11),
            new VowelSeqInfo(1, 1, 1, new int[]{76, 0, 0}, new int[]{11, 0, 0}, -1, 0, 0, 0),
            new VowelSeqInfo(1, 1, 1, new int[]{84, 0, 0}, new int[]{12, 0, 0}, -1, 0, -1, 0),
            new VowelSeqInfo(2, 1, 0, new int[]{1, 37, 0}, new int[]{1, 13, 0}, -1, 0, -1, 0),
            new VowelSeqInfo(2, 1, 0, new int[]{1, 47, 0}, new int[]{1, 14, 0}, -1, 0, -1, 0),
            new VowelSeqInfo(2, 1, 0, new int[]{1, 70, 0}, new int[]{1, 15, 0}, -1, 17, -1, 0),
            new VowelSeqInfo(2, 1, 0, new int[]{1, 84, 0}, new int[]{1, 16, 0}, -1, 18, -1, 0),
            new VowelSeqInfo(2, 1, 0, new int[]{7, 70, 0}, new int[]{2, 17, 0}, 0, 0, -1, 0),
            new VowelSeqInfo(2, 1, 0, new int[]{7, 84, 0}, new int[]{2, 18, 0}, 0, 0, -1, 0),
            new VowelSeqInfo(2, 1, 0, new int[]{23, 47, 0}, new int[]{4, 19, 0}, -1, 0, -1, 0),
            new VowelSeqInfo(2, 0, 0, new int[]{23, 70, 0}, new int[]{4, 20, 0}, -1, 21, -1, 0),
            new VowelSeqInfo(2, 1, 0, new int[]{29, 70, 0}, new int[]{5, 21, 0}, 0, 0, -1, 0),
            new VowelSeqInfo(2, 1, 0, new int[]{37, 1, 0}, new int[]{6, 22, 0}, -1, 0, -1, 0),
            new VowelSeqInfo(2, 0, 1, new int[]{37, 23, 0}, new int[]{6, 23, 0}, -1, 24, -1, 0),
            new VowelSeqInfo(2, 1, 1, new int[]{37, 29, 0}, new int[]{6, 24, 0}, 1, 0, -1, 0),
            new VowelSeqInfo(2, 1, 0, new int[]{37, 70, 0}, new int[]{6, 25, 0}, -1, 0, -1, 0),
            new VowelSeqInfo(2, 1, 1, new int[]{47, 1, 0}, new int[]{7, 26, 0}, -1, 0, -1, 27),
            new VowelSeqInfo(2, 1, 1, new int[]{47, 13, 0}, new int[]{7, 27, 0}, -1, 0, 1, 0),
            new VowelSeqInfo(2, 1, 1, new int[]{47, 23, 0}, new int[]{7, 28, 0}, -1, 0, -1, 0),
            new VowelSeqInfo(2, 1, 0, new int[]{47, 37, 0}, new int[]{7, 29, 0}, -1, 30, -1, 31),
            new VowelSeqInfo(2, 1, 0, new int[]{53, 37, 0}, new int[]{8, 30, 0}, 0, 0, -1, 31),
            new VowelSeqInfo(2, 1, 0, new int[]{59, 37, 0}, new int[]{9, 31, 0}, -1, 30, 0, 0),
            new VowelSeqInfo(2, 1, 1, new int[]{70, 1, 0}, new int[]{10, 32, 0}, -1, 33, -1, 42),
            new VowelSeqInfo(2, 1, 1, new int[]{70, 7, 0}, new int[]{10, 33, 0}, 1, 0, -1, 0),
            new VowelSeqInfo(2, 0, 1, new int[]{70, 23, 0}, new int[]{10, 34, 0}, -1, 35, -1, 0),
            new VowelSeqInfo(2, 1, 1, new int[]{70, 29, 0}, new int[]{10, 35, 0}, 1, 0, -1, 0),
            new VowelSeqInfo(2, 1, 0, new int[]{70, 37, 0}, new int[]{10, 36, 0}, -1, 0, -1, 43),
            new VowelSeqInfo(2, 0, 1, new int[]{70, 47, 0}, new int[]{10, 37, 0}, -1, 38, -1, 44),
            new VowelSeqInfo(2, 1, 1, new int[]{70, 53, 0}, new int[]{10, 38, 0}, 1, 0, -1, 39),
            new VowelSeqInfo(2, 1, 1, new int[]{70, 59, 0}, new int[]{10, 39, 0}, -1, 38, 1, 45),
            new VowelSeqInfo(2, 0, 0, new int[]{70, 70, 0}, new int[]{10, 40, 0}, -1, 0, -1, 46),
            new VowelSeqInfo(2, 1, 1, new int[]{70, 84, 0}, new int[]{10, 41, 0}, -1, 0, -1, 0),
            new VowelSeqInfo(2, 1, 0, new int[]{76, 1, 0}, new int[]{11, 42, 0}, -1, 0, 0, 0),
            new VowelSeqInfo(2, 1, 0, new int[]{76, 37, 0}, new int[]{11, 43, 0}, -1, 0, 0, 0),
            new VowelSeqInfo(2, 0, 1, new int[]{76, 47, 0}, new int[]{11, 44, 0}, -1, 0, 0, 45),
            new VowelSeqInfo(2, 1, 1, new int[]{76, 59, 0}, new int[]{11, 45, 0}, -1, 0, 0, 0),
            new VowelSeqInfo(2, 1, 0, new int[]{76, 70, 0}, new int[]{11, 46, 0}, -1, 0, 0, 0),
            new VowelSeqInfo(2, 0, 1, new int[]{84, 23, 0}, new int[]{12, 47, 0}, -1, 48, -1, 0),
            new VowelSeqInfo(2, 1, 1, new int[]{84, 29, 0}, new int[]{12, 48, 0}, 1, 0, -1, 0),
            new VowelSeqInfo(3, 0, 0, new int[]{37, 23, 70}, new int[]{6, 23, 49}, -1, 50, -1, 0),
            new VowelSeqInfo(3, 1, 0, new int[]{37, 29, 70}, new int[]{6, 24, 50}, 1, 0, -1, 0),
            new VowelSeqInfo(3, 1, 0, new int[]{47, 1, 37}, new int[]{7, 26, 51}, -1, 0, -1, 0),
            new VowelSeqInfo(3, 1, 0, new int[]{47, 1, 84}, new int[]{7, 26, 52}, -1, 0, -1, 0),
            new VowelSeqInfo(3, 1, 0, new int[]{47, 23, 47}, new int[]{7, 28, 53}, -1, 0, -1, 0),
            new VowelSeqInfo(3, 0, 0, new int[]{70, 1, 84}, new int[]{10, 32, 54}, -1, 55, -1, 0),
            new VowelSeqInfo(3, 1, 0, new int[]{70, 7, 84}, new int[]{10, 33, 55}, 1, 0, -1, 0),
            new VowelSeqInfo(3, 0, 0, new int[]{70, 47, 37}, new int[]{10, 37, 56}, -1, 58, -1, 65),
            new VowelSeqInfo(3, 0, 0, new int[]{70, 47, 70}, new int[]{10, 37, 57}, -1, 0, -1, 45),
            new VowelSeqInfo(3, 1, 0, new int[]{70, 53, 37}, new int[]{10, 38, 58}, 1, 0, -1, 59),
            new VowelSeqInfo(3, 0, 0, new int[]{70, 59, 37}, new int[]{10, 39, 59}, -1, 58, 1, 65),
            new VowelSeqInfo(3, 0, 0, new int[]{70, 59, 70}, new int[]{10, 39, 60}, -1, 0, 1, 66),
            new VowelSeqInfo(3, 1, 0, new int[]{70, 84, 1}, new int[]{10, 41, 61}, -1, 0, -1, 0),
            new VowelSeqInfo(3, 0, 1, new int[]{70, 84, 23}, new int[]{10, 41, 62}, -1, 63, -1, 0),
            new VowelSeqInfo(3, 1, 1, new int[]{70, 84, 29}, new int[]{10, 41, 63}, 2, 0, -1, 0),
            new VowelSeqInfo(3, 1, 0, new int[]{70, 84, 70}, new int[]{10, 41, 64}, -1, 0, -1, 0),
            new VowelSeqInfo(3, 0, 0, new int[]{76, 47, 37}, new int[]{11, 44, 65}, -1, 0, 0, 65),
            new VowelSeqInfo(3, 0, 0, new int[]{76, 47, 70}, new int[]{11, 44, 45}, -1, 0, 0, 66),
            new VowelSeqInfo(3, 1, 0, new int[]{76, 59, 37}, new int[]{11, 45, 65}, -1, 0, 0, 0),
            new VowelSeqInfo(3, 1, 0, new int[]{76, 59, 70}, new int[]{11, 45, 66}, -1, 0, 0, 0),
            new VowelSeqInfo(3, 0, 0, new int[]{84, 23, 70}, new int[]{12, 47, 69}, -1, 70, -1, 0),
            new VowelSeqInfo(3, 1, 0, new int[]{84, 29, 70}, new int[]{12, 48, 70}, 1, 0, -1, 0)
    };

    /**
     *
     */
    public class ConSeqInfo {

        int len;
        int cLex[];
        boolean suffix; // có cho 

        public ConSeqInfo() {
        }

        public ConSeqInfo(int len, int cLex[], boolean suffix) {
            this.len = len;
            this.cLex = cLex;
            this.suffix = suffix;
        }
    };

//    VowelSeqInfo VSeqList[] = {
//        new VowelSeqInfo(1, 1, 1, new VnLexiName[]{VnLexiName.vnl_a, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_a, VowelSeq.vs_nil, VowelSeq.vs_nil}, -1, VowelSeq.vs_ar, -1, 3),
//        new VowelSeqInfo(1, 1, 1, new VnLexiName[]{VnLexiName.vnl_ar, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_ar, VowelSeq.vs_nil, VowelSeq.vs_nil}, 0, VowelSeq.vs_nil, -1, 3),
//        new VowelSeqInfo(1, 1, 1, new VnLexiName[]{VnLexiName.vnl_ab, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{3, VowelSeq.vs_nil, VowelSeq.vs_nil}, -1, VowelSeq.vs_ar, 0, VowelSeq.vs_nil),
//        new VowelSeqInfo(1, 1, 1, new VnLexiName[]{VnLexiName.vnl_e, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_e, VowelSeq.vs_nil, VowelSeq.vs_nil}, -1, VowelSeq.vs_er, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(1, 1, 1, new VnLexiName[]{VnLexiName.vnl_er, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_er, VowelSeq.vs_nil, VowelSeq.vs_nil}, 0, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(1, 1, 1, new VnLexiName[]{VnLexiName.vnl_i, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_i, VowelSeq.vs_nil, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(1, 1, 1, new VnLexiName[]{VnLexiName.vnl_o, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_o, VowelSeq.vs_nil, VowelSeq.vs_nil}, -1, VowelSeq.vs_or, -1, VowelSeq.vs_oh),
//        new VowelSeqInfo(1, 1, 1, new VnLexiName[]{VnLexiName.vnl_or, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_or, VowelSeq.vs_nil, VowelSeq.vs_nil}, 0, VowelSeq.vs_nil, -1, VowelSeq.vs_oh),
//        new VowelSeqInfo(1, 1, 1, new VnLexiName[]{VnLexiName.vnl_oh, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_oh, VowelSeq.vs_nil, VowelSeq.vs_nil}, -1, VowelSeq.vs_or, 0, VowelSeq.vs_nil),
//        new VowelSeqInfo(1, 1, 1, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_nil, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_uh),
//        new VowelSeqInfo(1, 1, 1, new VnLexiName[]{VnLexiName.vnl_uh, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_uh, VowelSeq.vs_nil, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, 0, VowelSeq.vs_nil),
//        new VowelSeqInfo(1, 1, 1, new VnLexiName[]{VnLexiName.vnl_y, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_y, VowelSeq.vs_nil, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_a, VnLexiName.vnl_i, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_a, VowelSeq.vs_ai, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_a, VnLexiName.vnl_o, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_a, VowelSeq.vs_ao, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_a, VnLexiName.vnl_u, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_a, VowelSeq.vs_au, VowelSeq.vs_nil}, -1, VowelSeq.vs_aru, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_a, VnLexiName.vnl_y, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_a, VowelSeq.vs_ay, VowelSeq.vs_nil}, -1, VowelSeq.vs_ary, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_ar, VnLexiName.vnl_u, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_ar, VowelSeq.vs_aru, VowelSeq.vs_nil}, 0, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_ar, VnLexiName.vnl_y, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_ar, VowelSeq.vs_ary, VowelSeq.vs_nil}, 0, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_e, VnLexiName.vnl_o, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_e, VowelSeq.vs_eo, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(2, 0, 0, new VnLexiName[]{VnLexiName.vnl_e, VnLexiName.vnl_u, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_e, VowelSeq.vs_eu, VowelSeq.vs_nil}, -1, VowelSeq.vs_eru, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_er, VnLexiName.vnl_u, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_er, VowelSeq.vs_eru, VowelSeq.vs_nil}, 0, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_i, VnLexiName.vnl_a, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_i, VowelSeq.vs_ia, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(2, 0, 1, new VnLexiName[]{VnLexiName.vnl_i, VnLexiName.vnl_e, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_i, VowelSeq.vs_ie, VowelSeq.vs_nil}, -1, VowelSeq.vs_ier, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(2, 1, 1, new VnLexiName[]{VnLexiName.vnl_i, VnLexiName.vnl_er, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_i, VowelSeq.vs_ier, VowelSeq.vs_nil}, 1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_i, VnLexiName.vnl_u, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_i, VowelSeq.vs_iu, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(2, 1, 1, new VnLexiName[]{VnLexiName.vnl_o, VnLexiName.vnl_a, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_o, VowelSeq.vs_oa, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_oab),
//        new VowelSeqInfo(2, 1, 1, new VnLexiName[]{VnLexiName.vnl_o, VnLexiName.vnl_ab, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_o, VowelSeq.vs_oab, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, 1, VowelSeq.vs_nil),
//        new VowelSeqInfo(2, 1, 1, new VnLexiName[]{VnLexiName.vnl_o, VnLexiName.vnl_e, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_o, VowelSeq.vs_oe, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_o, VnLexiName.vnl_i, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_o, VowelSeq.vs_oi, VowelSeq.vs_nil}, -1, VowelSeq.vs_ori, -1, VowelSeq.vs_ohi),
//        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_or, VnLexiName.vnl_i, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_or, VowelSeq.vs_ori, VowelSeq.vs_nil}, 0, VowelSeq.vs_nil, -1, VowelSeq.vs_ohi),
//        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_oh, VnLexiName.vnl_i, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_oh, VowelSeq.vs_ohi, VowelSeq.vs_nil}, -1, VowelSeq.vs_ori, 0, VowelSeq.vs_nil),
//        new VowelSeqInfo(2, 1, 1, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_a, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_ua, VowelSeq.vs_nil}, -1, VowelSeq.vs_uar, -1, VowelSeq.vs_uha),
//        new VowelSeqInfo(2, 1, 1, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_ar, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uar, VowelSeq.vs_nil}, 1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(2, 0, 1, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_e, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_ue, VowelSeq.vs_nil}, -1, VowelSeq.vs_uer, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(2, 1, 1, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_er, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uer, VowelSeq.vs_nil}, 1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_i, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_ui, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_uhi),
//        new VowelSeqInfo(2, 0, 1, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_o, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uo, VowelSeq.vs_nil}, -1, VowelSeq.vs_uor, -1, VowelSeq.vs_uho),
//        new VowelSeqInfo(2, 1, 1, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_or, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uor, VowelSeq.vs_nil}, 1, VowelSeq.vs_nil, -1, VowelSeq.vs_uoh),
//        new VowelSeqInfo(2, 1, 1, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_oh, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uoh, VowelSeq.vs_nil}, -1, VowelSeq.vs_uor, 1, VowelSeq.vs_uhoh),
//        new VowelSeqInfo(2, 0, 0, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_u, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uu, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_uhu),
//        new VowelSeqInfo(2, 1, 1, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_y, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uy, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_uh, VnLexiName.vnl_a, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_uh, VowelSeq.vs_uha, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, 0, VowelSeq.vs_nil),
//        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_uh, VnLexiName.vnl_i, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_uh, VowelSeq.vs_uhi, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, 0, VowelSeq.vs_nil),
//        new VowelSeqInfo(2, 0, 1, new VnLexiName[]{VnLexiName.vnl_uh, VnLexiName.vnl_o, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_uh, VowelSeq.vs_uho, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, 0, VowelSeq.vs_uhoh),
//        new VowelSeqInfo(2, 1, 1, new VnLexiName[]{VnLexiName.vnl_uh, VnLexiName.vnl_oh, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_uh, VowelSeq.vs_uhoh, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, 0, VowelSeq.vs_nil),
//        new VowelSeqInfo(2, 1, 0, new VnLexiName[]{VnLexiName.vnl_uh, VnLexiName.vnl_u, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_uh, VowelSeq.vs_uhu, VowelSeq.vs_nil}, -1, VowelSeq.vs_nil, 0, VowelSeq.vs_nil),
//        new VowelSeqInfo(2, 0, 1, new VnLexiName[]{VnLexiName.vnl_y, VnLexiName.vnl_e, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_y, VowelSeq.vs_ye, VowelSeq.vs_nil}, -1, VowelSeq.vs_yer, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(2, 1, 1, new VnLexiName[]{VnLexiName.vnl_y, VnLexiName.vnl_er, VnLexiName.vnl_nonVnChar}, new VowelSeq[]{VowelSeq.vs_y, VowelSeq.vs_yer, VowelSeq.vs_nil}, 1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(3, 0, 0, new VnLexiName[]{VnLexiName.vnl_i, VnLexiName.vnl_e, VnLexiName.vnl_u}, new VowelSeq[]{VowelSeq.vs_i, VowelSeq.vs_ie, VowelSeq.vs_ieu}, -1, VowelSeq.vs_ieru, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(3, 1, 0, new VnLexiName[]{VnLexiName.vnl_i, VnLexiName.vnl_er, VnLexiName.vnl_u}, new VowelSeq[]{VowelSeq.vs_i, VowelSeq.vs_ier, VowelSeq.vs_ieru}, 1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(3, 1, 0, new VnLexiName[]{VnLexiName.vnl_o, VnLexiName.vnl_a, VnLexiName.vnl_i}, new VowelSeq[]{VowelSeq.vs_o, VowelSeq.vs_oa, VowelSeq.vs_oai}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(3, 1, 0, new VnLexiName[]{VnLexiName.vnl_o, VnLexiName.vnl_a, VnLexiName.vnl_y}, new VowelSeq[]{VowelSeq.vs_o, VowelSeq.vs_oa, VowelSeq.vs_oay}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(3, 1, 0, new VnLexiName[]{VnLexiName.vnl_o, VnLexiName.vnl_e, VnLexiName.vnl_o}, new VowelSeq[]{VowelSeq.vs_o, VowelSeq.vs_oe, VowelSeq.vs_oeo}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(3, 0, 0, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_a, VnLexiName.vnl_y}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_ua, VowelSeq.vs_uay}, -1, VowelSeq.vs_uary, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(3, 1, 0, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_ar, VnLexiName.vnl_y}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uar, VowelSeq.vs_uary}, 1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(3, 0, 0, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_o, VnLexiName.vnl_i}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uo, VowelSeq.vs_uoi}, -1, VowelSeq.vs_uori, -1, VowelSeq.vs_uhoi),
//        new VowelSeqInfo(3, 0, 0, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_o, VnLexiName.vnl_u}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uo, VowelSeq.vs_uou}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_uhou),
//        new VowelSeqInfo(3, 1, 0, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_or, VnLexiName.vnl_i}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uor, VowelSeq.vs_uori}, 1, VowelSeq.vs_nil, -1, VowelSeq.vs_uohi),
//        new VowelSeqInfo(3, 0, 0, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_oh, VnLexiName.vnl_i}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uoh, VowelSeq.vs_uohi}, -1, VowelSeq.vs_uori, 1, VowelSeq.vs_uhohi),
//        new VowelSeqInfo(3, 0, 0, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_oh, VnLexiName.vnl_u}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uoh, VowelSeq.vs_uohu}, -1, VowelSeq.vs_nil, 1, VowelSeq.vs_uhohu),
//        new VowelSeqInfo(3, 1, 0, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_y, VnLexiName.vnl_a}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uy, VowelSeq.vs_uya}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(3, 0, 1, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_y, VnLexiName.vnl_e}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uy, VowelSeq.vs_uye}, -1, VowelSeq.vs_uyer, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(3, 1, 1, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_y, VnLexiName.vnl_er}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uy, VowelSeq.vs_uyer}, 2, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(3, 1, 0, new VnLexiName[]{VnLexiName.vnl_u, VnLexiName.vnl_y, VnLexiName.vnl_u}, new VowelSeq[]{VowelSeq.vs_u, VowelSeq.vs_uy, VowelSeq.vs_uyu}, -1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(3, 0, 0, new VnLexiName[]{VnLexiName.vnl_uh, VnLexiName.vnl_o, VnLexiName.vnl_i}, new VowelSeq[]{VowelSeq.vs_uh, VowelSeq.vs_uho, VowelSeq.vs_uhoi}, -1, VowelSeq.vs_nil, 0, VowelSeq.vs_uhohi),
//        new VowelSeqInfo(3, 0, 0, new VnLexiName[]{VnLexiName.vnl_uh, VnLexiName.vnl_o, VnLexiName.vnl_u}, new VowelSeq[]{VowelSeq.vs_uh, VowelSeq.vs_uho, VowelSeq.vs_uhou}, -1, VowelSeq.vs_nil, 0, VowelSeq.vs_uhohu),
//        new VowelSeqInfo(3, 1, 0, new VnLexiName[]{VnLexiName.vnl_uh, VnLexiName.vnl_oh, VnLexiName.vnl_i}, new VowelSeq[]{VowelSeq.vs_uh, VowelSeq.vs_uhoh, VowelSeq.vs_uhohi}, -1, VowelSeq.vs_nil, 0, VowelSeq.vs_nil),
//        new VowelSeqInfo(3, 1, 0, new VnLexiName[]{VnLexiName.vnl_uh, VnLexiName.vnl_oh, VnLexiName.vnl_u}, new VowelSeq[]{VowelSeq.vs_uh, VowelSeq.vs_uhoh, VowelSeq.vs_uhohu}, -1, VowelSeq.vs_nil, 0, VowelSeq.vs_nil),
//        new VowelSeqInfo(3, 0, 0, new VnLexiName[]{VnLexiName.vnl_y, VnLexiName.vnl_e, VnLexiName.vnl_u}, new VowelSeq[]{VowelSeq.vs_y, VowelSeq.vs_ye, VowelSeq.vs_yeu}, -1, VowelSeq.vs_yeru, -1, VowelSeq.vs_nil),
//        new VowelSeqInfo(3, 1, 0, new VnLexiName[]{VnLexiName.vnl_y, VnLexiName.vnl_er, VnLexiName.vnl_u}, new VowelSeq[]{VowelSeq.vs_y, VowelSeq.vs_yer, VowelSeq.vs_yeru}, 1, VowelSeq.vs_nil, -1, VowelSeq.vs_nil)
//    };

    ConSeqInfo CSeqList[] = {
            new ConSeqInfo(1, new int[]{19, 0, 0}, true),
            new ConSeqInfo(1, new int[]{20, 0, 0}, true),
            new ConSeqInfo(2, new int[]{20, 36, 0}, true),
            new ConSeqInfo(1, new int[]{21, 0, 0}, false),
            new ConSeqInfo(1, new int[]{22, 0, 0}, false),
            new ConSeqInfo(1, new int[]{35, 0, 0}, false),
            new ConSeqInfo(2, new int[]{35, 36, 0}, false),
            new ConSeqInfo(2, new int[]{35, 37, 0}, false),
            new ConSeqInfo(3, new int[]{35, 37, 46}, false),
            new ConSeqInfo(1, new int[]{36, 0, 0}, false),
            new ConSeqInfo(1, new int[]{43, 0, 0}, false),
            new ConSeqInfo(2, new int[]{43, 36, 0}, false),
            new ConSeqInfo(1, new int[]{44, 0, 0}, false),
            new ConSeqInfo(1, new int[]{45, 0, 0}, true),
            new ConSeqInfo(1, new int[]{46, 0, 0}, true),
            new ConSeqInfo(2, new int[]{46, 35, 0}, true),
            new ConSeqInfo(3, new int[]{46, 35, 36}, false),
            new ConSeqInfo(2, new int[]{46, 36, 0}, true),
            new ConSeqInfo(1, new int[]{65, 0, 0}, true),
            new ConSeqInfo(2, new int[]{65, 36, 0}, false),
            new ConSeqInfo(1, new int[]{66, 0, 0}, false),
            new ConSeqInfo(2, new int[]{66, 70, 0}, false),
            new ConSeqInfo(1, new int[]{67, 0, 0}, false),
            new ConSeqInfo(1, new int[]{68, 0, 0}, false),
            new ConSeqInfo(1, new int[]{69, 0, 0}, true),
            new ConSeqInfo(2, new int[]{69, 36, 0}, false),
            new ConSeqInfo(2, new int[]{69, 67, 0}, false),
            new ConSeqInfo(1, new int[]{82, 0, 0}, false),
            new ConSeqInfo(1, new int[]{83, 0, 0}, false)
    };
//    /**
//     * Các chuỗi phụ âm có thể có
//     */
//    ConSeqInfo CSeqList[] = {
//        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_b, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, true),
//        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_c, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, true),
//        new ConSeqInfo(2, new VnLexiName[]{VnLexiName.vnl_c, VnLexiName.vnl_h, VnLexiName.vnl_nonVnChar}, true),
//        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_d, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, false),
//        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_dd, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, false),
//        new ConSeqInfo(2, new VnLexiName[]{VnLexiName.vnl_d, VnLexiName.vnl_z, VnLexiName.vnl_nonVnChar}, false),
//        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_g, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, false),
//        new ConSeqInfo(2, new VnLexiName[]{VnLexiName.vnl_g, VnLexiName.vnl_h, VnLexiName.vnl_nonVnChar}, false),
//        new ConSeqInfo(2, new VnLexiName[]{VnLexiName.vnl_g, VnLexiName.vnl_i, VnLexiName.vnl_nonVnChar}, false),
//        new ConSeqInfo(3, new VnLexiName[]{VnLexiName.vnl_g, VnLexiName.vnl_i, VnLexiName.vnl_n}, false),
//        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_h, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, false),
//        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_k, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, false),
//        new ConSeqInfo(2, new VnLexiName[]{VnLexiName.vnl_k, VnLexiName.vnl_h, VnLexiName.vnl_nonVnChar}, false),
//        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_l, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, false),
//        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_m, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, true),
//        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_n, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, true),
//        new ConSeqInfo(2, new VnLexiName[]{VnLexiName.vnl_n, VnLexiName.vnl_g, VnLexiName.vnl_nonVnChar}, true),
//        new ConSeqInfo(3, new VnLexiName[]{VnLexiName.vnl_n, VnLexiName.vnl_g, VnLexiName.vnl_h}, false),
//        new ConSeqInfo(2, new VnLexiName[]{VnLexiName.vnl_n, VnLexiName.vnl_h, VnLexiName.vnl_nonVnChar}, true),
//        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_p, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, true),
//        new ConSeqInfo(2, new VnLexiName[]{VnLexiName.vnl_p, VnLexiName.vnl_h, VnLexiName.vnl_nonVnChar}, false),
//        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_q, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, false),
//        new ConSeqInfo(2, new VnLexiName[]{VnLexiName.vnl_q, VnLexiName.vnl_u, VnLexiName.vnl_nonVnChar}, false),
//        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_r, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, false),
//        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_s, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, false),
//        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_t, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, true),
//        new ConSeqInfo(2, new VnLexiName[]{VnLexiName.vnl_t, VnLexiName.vnl_h, VnLexiName.vnl_nonVnChar}, false),
//        new ConSeqInfo(2, new VnLexiName[]{VnLexiName.vnl_t, VnLexiName.vnl_r, VnLexiName.vnl_nonVnChar}, false),
//        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_v, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, false),
//        new ConSeqInfo(1, new VnLexiName[]{VnLexiName.vnl_x, VnLexiName.vnl_nonVnChar, VnLexiName.vnl_nonVnChar}, false)
//    };

    public int VSeqCount = VSeqList.length;

    public int CSeqCount = CSeqList.length;

    /**
     * Lưu chuỗi 3 nguyên âm vào trong mảng v[] rồi cho vào hàm lookupVSeq, trả
     * lại chuỗi nguyên âm vs (nếu có)
     */
    class VSeqPair {

        int vLex[];
        int vSeq;

        public VSeqPair() {
        }

        public VSeqPair(int vLex[], int vSeq) {
            this.vLex = vLex;
            this.vSeq = vSeq;
        }
    };
    VSeqPair SortedVSeqList[] = new VSeqPair[VSeqCount];

    /**
     * Tương tự với VSeqPair
     */
    class CSeqPair {

        int cLex[];
        int cSeq;

        public CSeqPair() {
        }

        public CSeqPair(int cLex[], int cSeq) {
            this.cLex = cLex;
            this.cSeq = cSeq;
        }
    };
    CSeqPair SortedCSeqList[] = new CSeqPair[CSeqCount];

    /**
     * Vowel-Consonant Pair
     */
    class VCPair {

        int vSeq;
        int cSeq;

        public VCPair() {
        }

        public VCPair(int vSeq, int cSeq) {
            this.vSeq = vSeq;
            this.cSeq = cSeq;
        }
    };

    VCPair VCPairList[] = {
        new VCPair(1, 2), new VCPair(1, 3), new VCPair(1, 15), new VCPair(1, 16), new VCPair(1, 17), 
        new VCPair(1, 19), new VCPair(1, 20), new VCPair(1, 26),
        new VCPair(2, 2), new VCPair(2, 3), new VCPair(2, 16), new VCPair(2, 17), 
        new VCPair(2, 20), new VCPair(2, 26),
        new VCPair(3, 2), new VCPair(3, 15), new VCPair(3, 16), new VCPair(3, 17), new VCPair(3, 20), new VCPair(3, 26),
        new VCPair(4, 2), new VCPair(4, 3), new VCPair(4, 15), new VCPair(4, 16), new VCPair(4, 17),
        new VCPair(4, 19), new VCPair(4, 20), new VCPair(4, 26),
        new VCPair(5, 2), new VCPair(5, 3), new VCPair(5, 15), new VCPair(5, 16), new VCPair(5, 19),
        new VCPair(5, 20), new VCPair(5, 26),
        new VCPair(6, 2), new VCPair(6, 3), new VCPair(6, 15), new VCPair(6, 16), new VCPair(6, 19), new VCPair(6, 20), new VCPair(6, 26),
        new VCPair(7, 2), new VCPair(7, 15), new VCPair(7, 16), new VCPair(7, 17), new VCPair(7, 20), new VCPair(7, 26),
        new VCPair(8, 2), new VCPair(8, 15), new VCPair(8, 16), new VCPair(8, 17), new VCPair(8, 20), new VCPair(8, 26),
        new VCPair(9, 15), new VCPair(9, 16), new VCPair(9, 20), new VCPair(9, 26),
        new VCPair(10, 2), new VCPair(10, 15), new VCPair(10, 16), new VCPair(10, 17), new VCPair(10, 20), new VCPair(10, 26),
        new VCPair(11, 2), new VCPair(11, 15), new VCPair(11, 16), new VCPair(11, 17), new VCPair(11, 26),
        new VCPair(12, 26),
        new VCPair(23, 2), new VCPair(23, 15), new VCPair(23, 16), new VCPair(23, 17), new VCPair(23, 20), new VCPair(23, 26),
        new VCPair(24, 2), new VCPair(24, 15), new VCPair(24, 16), new VCPair(24, 17), new VCPair(24, 20), new VCPair(24, 26),
        new VCPair(26, 2), new VCPair(26, 3), new VCPair(26, 15), new VCPair(26, 16), new VCPair(26, 17),
        new VCPair(26, 19), new VCPair(26, 20), new VCPair(26, 26),
        new VCPair(27, 2), new VCPair(27, 15), new VCPair(27, 16), new VCPair(27, 17), new VCPair(27, 26),
        new VCPair(28, 16), new VCPair(28, 26),
        new VCPair(32, 16), new VCPair(32, 17), new VCPair(32, 26),
        new VCPair(33, 16), new VCPair(33, 17), new VCPair(33, 26),
        new VCPair(34, 2), new VCPair(34, 3), new VCPair(34, 16), new VCPair(34, 19),
        new VCPair(35, 2), new VCPair(35, 3), new VCPair(35, 16), new VCPair(35, 19),
        new VCPair(37, 2), new VCPair(37, 15), new VCPair(37, 16), new VCPair(37, 17), new VCPair(37, 20), new VCPair(37, 26),
        new VCPair(38, 2), new VCPair(38, 15), new VCPair(38, 16), new VCPair(38, 17), new VCPair(38, 26),
        new VCPair(44, 2), new VCPair(44, 15), new VCPair(44, 16), new VCPair(44, 17), new VCPair(44, 20), new VCPair(44, 26),
        new VCPair(45, 2), new VCPair(45, 15), new VCPair(45, 16), new VCPair(45, 17), new VCPair(45, 20), new VCPair(45, 26),
        new VCPair(41, 2), new VCPair(41, 3), new VCPair(41, 16), new VCPair(41, 19), new VCPair(41, 20), new VCPair(41, 26),
        new VCPair(47, 15), new VCPair(47, 16), new VCPair(47, 17), new VCPair(47, 20), new VCPair(47, 26),
        new VCPair(48, 15), new VCPair(48, 16), new VCPair(48, 17), new VCPair(48, 26),
        new VCPair(62, 16), new VCPair(62, 26),
        new VCPair(63, 16), new VCPair(63, 26)
    };
    
    public int VCPairCount = VCPairList.length;

    //------------------------------------------------
    public int lookupVSeq(int vLex1, int vLex2, int vLex3) {
        String lookUp = VnLexiName[vLex1] + VnLexiName[vLex2] + VnLexiName[vLex3] + "";
        
        if(vLex2 == 0 ){
            for(int i = 1; i <= 12; i++ ){
                if(VowelSeq[i].equals(lookUp)) return i;
            }
        }
        else if (vLex3 == 0) {
            for(int i = 13; i <= 48; i++ ){
                if(VowelSeq[i].equals(lookUp)) return i;
            }
        }
        else {
            for(int i = 49; i <= 70; i++ ){
                if(VowelSeq[i].equals(lookUp)) return i;
            }
        }
        return 0;
    }

//------------------------------------------------
    public int lookupCSeq(int cLex1, int cLex2, int cLex3) {
        String lookUp = VnLexiName[cLex1] + VnLexiName[cLex2] + VnLexiName[cLex3] + "";
        
        for(int i = 1; i <= 30; i++ ){
            if(ConSeq[i].equals(lookUp)) return i;
        }
        
        return 0;
    }

//------------------------------------------------
    int tripleVowelCompare(VSeqPair p1, VSeqPair p2) {
        VSeqPair t1 = p1;
        VSeqPair t2 = p2;

        for (int i=0; i<3; i++) {
            if (t1.vLex[i] < t2.vLex[i])
                return -1;
            if (t1.vLex[i] > t2.vLex[i])
                return 1;
        }
        return 0;
    }

//------------------------------------------------
    int tripleConCompare(CSeqPair p1, CSeqPair p2) {
        CSeqPair t1 = p1;
        CSeqPair t2 = p2;

        for (int i=0; i<3; i++) {
            if (t1.cLex[i] < t2.cLex[i])
                return -1;
            if (t1.cLex[i] > t2.cLex[i])
                return 1;
        }
        return 0;
    }
    
    int compareSeq(int[] seq1, int[] seq2) {
        for(int i = 0; i < 3; i++) {
            if(seq1[i] < seq2[i]) return -1;
            if(seq1[i] > seq2[i]) return 1;
        }
        return 0;
    }

//------------------------------------------------
    int VCPairCompare(VCPair p1, VCPair p2) {
        VCPair t1 = p1;
        VCPair t2 = p2;

        if (t1.vSeq < t2.vSeq)
            return -1;
        if (t1.vSeq > t2.vSeq)
          return 1;

        if (t1.cSeq < t2.cSeq)
            return -1;
        if (t1.cSeq > t2.cSeq)
            return 1;
        return 0;
    }

//----------------------------------------------------------
    boolean isValidCV(int cSeq, int vSeq) {
        if (cSeq == 0 || vSeq == 0) { // nếu CSeq hoặc VSeq rỗng thì true
            return true;
        }

        VowelSeqInfo vInfo = new VowelSeqInfo(); //= VSeqList[v]; // Lấy info của VSeq trong VList

        if ((cSeq == 9 && vInfo.vSeq[0] == 37 /*i*/)
                || (cSeq == 23 && vInfo.vSeq[0] == 70 /*u*/)) { // nếu gi+i || qu+u -> false
            return false; // gi doesn't go with i, qu doesn't go with u
        }
        if (cSeq == 12) {
            // k can only go with the following vowel sequences
            int kVseq[] = {4, 6, 12, 5, 19, 20, 21, 22, 23, 24, 49, 50, 0};
            // đi sau k: e, i, y, ê, eo, eu, êu, ia, ie, iê, ieu, iêu, 
            int i;
            for (i = 0; kVseq[i] != 0 && kVseq[i] != vSeq; i++);
            return (kVseq[i] != 0);
        }

        //More checks
        return true;
    }

//----------------------------------------------------------
    boolean isValidVC(int vSeq, int cSeq) {
        if (vSeq == 0 || cSeq == 0) {
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
        p.vSeq = vSeq;
        p.cSeq = cSeq;
//        if (bsearch(&p, VCPairList, VCPairCount, sizeof(VCPair), VCPairCompare))
//            return true;

        return false;
    }
    
    public int binarySearchVowelSeq(int[] key, int size) {
        int low = 0;
        int high = size - 1;

        while (high > low) {
            int middle = (low + high) / 2;
            if (compareSeq(key, VSeqList[middle].vSeq) == 0) {
                return middle;
            }
            if (compareSeq(key, VSeqList[middle].vSeq) == 1) {
                low = middle + 1;
            }
            if (compareSeq(key, VSeqList[middle].vSeq) == -1) {
                high = middle - 1;
            }
        }
        return -1;
    }
    
    public int binarySearchConSeq(int[] key, int size) {
        int low = 0;
        int high = size - 1;

        while (high > low) {
            int middle = (low + high) / 2;
            if (compareSeq(key, CSeqList[middle].cLex) == 0) {
                return middle;
            }
            if (compareSeq(key, CSeqList[middle].cLex) == 1) {
                low = middle + 1;
            }
            if (compareSeq(key, CSeqList[middle].cLex) == -1) {
                high = middle - 1;
            }
        }
        return -1;
    }
    
    public int getLexPos(char key) {
        int size = VNLEXICOUNT;
        for(int i = 0; i < size; i++){
            if(VnLexiName[i] == key) return i;
        }
        return -1;
    }

//----------------------------------------------------------
//    boolean isValidCVC(int cSeq1, int vSeq, int cSeq2) {
//        if (vSeq == 0) {
//            return (cSeq1 == 0 || cSeq2 != 0);
//        }
//
//        if (cSeq1 == 0) {
//            return isValidVC(vSeq, cSeq2);
//        }
//
//        if (cSeq2 == 0) {
//            return isValidCV(cSeq1, vSeq);
//        }
//
//        boolean okCV = isValidCV(cSeq1, vSeq);
//        boolean okVC = isValidVC(vSeq, cSeq2);
//
//        if (okCV && okVC) {
//            return true;
//        }
//
//        if (!okVC) {
//            //check some exceptions: vc fails but cvc passes
//
//            // quyn, quynh
//            if (cSeq1 == 23 /**/ && vSeq == 12 && (cSeq2 == 16 || cSeq2 == 19)) {
//                return true;
//            }
//
//            // gieng, gie^ng
//            if (cSeq1 == 9 && (vSeq == 4 || vSeq == 5) && (cSeq2 == 16 || cSeq2 == 17)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public int processRoof(UkKeyEvent & ev) {
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
//    int vSeq, newVSeq;
//    int i, vStart, vEnd;
//    int curTonePos, newTonePos, tone;
//    int changePos;
//    boolean roofRemoved = false;
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
//int processHookWithUO(UkKeyEvent & ev)
//{
//    int vSeq, newVSeq;
//    int i, vStart, vEnd;
//    int curTonePos, newTonePos, tone;
//    boolean hookRemoved = false;
//    boolean removeWithUndo = true;
//    boolean toneRemoved = false;
//    
//    (void)toneRemoved; // fix warning
//    
//    VnLexiName v;
//
//    if (!m_pCtrl->options.freeMarking && m_buffer[m_current].vOffset != 0)
//        return processAppend(ev);    
//
//    vEnd = m_current - m_buffer[m_current].vOffset;
//    vs = m_buffer[vEnd].vseq;
//    vStart = vEnd - (VSeqList[vs].len - 1);
//    v = VSeqList[vs].v;
//    curTonePos = vStart + getTonePosition(vs, vEnd == m_current);
//    tone = m_buffer[curTonePos].tone;
//
//    switch (ev.evType) {
//    case vneHook_u:
//        if (v[0] == vnl_u) {
//            newVs = VSeqList[vs].withHook;
//            markChange(vStart);
//            m_buffer[vStart].vnSym = vnl_uh;
//        }
//        else {// v[0] = vnl_uh, -> uo
//            newVs = lookupVSeq(vnl_u, vnl_o, v[2]);
//            markChange(vStart);
//            m_buffer[vStart].vnSym = vnl_u;
//            m_buffer[vStart+1].vnSym = vnl_o;
//            hookRemoved = true;
//            toneRemoved =  (m_buffer[vStart].tone != 0);
//        }
//        break;
//    case vneHook_o:
//        if (v[1] == vnl_o || v[1] == vnl_or) {
//            if (vEnd == m_current && VSeqList[vs].len == 2 && 
//                m_buffer[m_current].form == vnw_cv && m_buffer[m_current-2].cseq == cs_th)
//            {
//                // o|o^ -> o+
//                newVs = VSeqList[vs].withHook;
//                markChange(vStart+1);
//                m_buffer[vStart+1].vnSym = vnl_oh;
//            }
//            else {
//                newVs = lookupVSeq(vnl_uh, vnl_oh, v[2]);
//                if (v[0] == vnl_u) {
//                    markChange(vStart);
//                    m_buffer[vStart].vnSym = vnl_uh;
//                    m_buffer[vStart+1].vnSym = vnl_oh;
//                }
//                else {
//                    markChange(vStart+1);
//                    m_buffer[vStart+1].vnSym = vnl_oh;
//                }
//            }
//        }
//        else {// v[1] = vnl_oh, -> uo
//            newVs = lookupVSeq(vnl_u, vnl_o, v[2]);
//            if (v[0] == vnl_uh) {
//                markChange(vStart);
//                m_buffer[vStart].vnSym = vnl_u;
//                m_buffer[vStart+1].vnSym = vnl_o;
//            }
//            else {
//                markChange(vStart+1);
//                m_buffer[vStart+1].vnSym = vnl_o;
//            }
//            hookRemoved = true;
//            toneRemoved = (m_buffer[vStart+1].tone != 0);
//        }
//        break;
//    default:  //vneHookAll, vneHookUO:
//        if (v[0] == vnl_u) {
//            if (v[1] == vnl_o || v[1] == vnl_or) { 
//                //uo -> uo+ if prefixed by "th"
//                if ((vs == vs_uo || vs == vs_uor) && vEnd == m_current && 
//                    m_buffer[m_current].form == vnw_cv && m_buffer[m_current-2].cseq == cs_th) 
//                {
//                    newVs = vs_uoh;
//                    markChange(vStart+1);
//                    m_buffer[vStart+1].vnSym = vnl_oh;
//                }
//                else {
//                    //uo -> u+o+
//                    newVs = VSeqList[vs].withHook;
//                    markChange(vStart);
//                    m_buffer[vStart].vnSym = vnl_uh;
//                    newVs = VSeqList[newVs].withHook;
//                    m_buffer[vStart+1].vnSym = vnl_oh;
//                }
//            }
//            else {//uo+ -> u+o+
//                newVs = VSeqList[vs].withHook;
//                markChange(vStart);
//                m_buffer[vStart].vnSym = vnl_uh;
//            }
//        }
//        else {//v[0] == vnl_uh
//            if (v[1] == vnl_o) { // u+o -> u+o+
//                newVs = VSeqList[vs].withHook;
//                markChange(vStart+1);
//                m_buffer[vStart+1].vnSym = vnl_oh;
//            }
//            else { //v[1] == vnl_oh, u+o+ -> uo
//                newVs = lookupVSeq(vnl_u, vnl_o, v[2]); //vs_uo;
//                markChange(vStart);
//                m_buffer[vStart].vnSym = vnl_u;
//                m_buffer[vStart+1].vnSym = vnl_o;
//                hookRemoved = true;
//                toneRemoved = (m_buffer[vStart].tone != 0 || m_buffer[vStart+1].tone != 0);
//            }
//        }
//        break;
//    }
//
//    VowelSeqInfo *p = &VSeqList[newVs];
//    for (i=0; i < p->len; i++) { //update sub-sequences
//        m_buffer[vStart+i].vseq = p->sub[i];
//    }
//
//    //check if tone re-position is needed
//    newTonePos = vStart + getTonePosition(newVs, vEnd == m_current);
//    /* //For now, users don't seem to like the following processing, thus commented out
//    if (hookRemoved && tone != 0 && (!p->complete || toneRemoved)) {
//        //remove tone if the vowel sequence becomes incomplete as a result of hook removal
//        //OR if a removed hook is at the same position as the current tone
//        markChange(curTonePos);
//        m_buffer[curTonePos].tone = 0;
//    }
//    else 
//    */
//    if (curTonePos != newTonePos && tone != 0) {
//        markChange(newTonePos);
//        m_buffer[newTonePos].tone = tone;
//        markChange(curTonePos);
//        m_buffer[curTonePos].tone = 0;
//    }
//
//    if (hookRemoved && removeWithUndo) {
//        m_singleMode = false;
//        processAppend(ev);
//        m_reverted = true;
//    }
//
//    return 1;
//}
//------------------------------------------------------------------
//int processHook(UkKeyEvent & ev)
//{
//    if (!m_pCtrl->vietKey || m_current < 0 || m_buffer[m_current].vOffset < 0)
//        return processAppend(ev);
//
//    VowelSeq vs, newVs;
//    int i, vStart, vEnd;
//    int curTonePos, newTonePos, tone;
//    int changePos;
//    bool hookRemoved = false;
//    VowelSeqInfo *pInfo;
//    VnLexiName *v;
//
//    vEnd = m_current - m_buffer[m_current].vOffset;
//    vs = m_buffer[vEnd].vseq;
//
//    v = VSeqList[vs].v;
//  
//    if (VSeqList[vs].len > 1 && 
//        ev.evType != vneBowl &&
//        (v[0] == vnl_u || v[0] == vnl_uh) &&
//        (v[1] == vnl_o || v[1] == vnl_oh || v[1] == vnl_or))
//        return processHookWithUO(ev);
//
//    vStart = vEnd - (VSeqList[vs].len - 1);
//    curTonePos = vStart + getTonePosition(vs, vEnd == m_current);
//    tone = m_buffer[curTonePos].tone;
//
//    newVs = VSeqList[vs].withHook;
//    if (newVs == vs_nil) {
//        if (VSeqList[vs].hookPos == -1)
//            return processAppend(ev); //hook is not applicable
//
//        //a hook already exists -> undo hook
//        VnLexiName curCh = m_buffer[vStart + VSeqList[vs].hookPos].vnSym;
//        VnLexiName newCh = (curCh == vnl_ab)? vnl_a : ((curCh == vnl_uh)? vnl_u : vnl_o);
//        changePos = vStart + VSeqList[vs].hookPos;
//        if (!m_pCtrl->options.freeMarking && changePos != m_current)
//            return processAppend(ev);
//
//        switch (ev.evType) {
//        case vneHook_u:
//            if (curCh != vnl_uh)
//                return processAppend(ev);
//            break;
//        case vneHook_o:
//            if (curCh != vnl_oh)
//                return processAppend(ev);
//            break;
//        case vneBowl:
//            if (curCh != vnl_ab)
//                return processAppend(ev);
//            break;
//        default:
//            if (ev.evType == vneHook_uo && curCh == vnl_ab)
//                return processAppend(ev);
//        }
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
//        hookRemoved = true;
//    }
//    else {
//        pInfo = &VSeqList[newVs];
//
//        switch (ev.evType) {
//        case vneHook_u:
//            if (pInfo->v[pInfo->hookPos] != vnl_uh)
//                return processAppend(ev);
//            break;
//        case vneHook_o:
//            if (pInfo->v[pInfo->hookPos] != vnl_oh)
//                return processAppend(ev);
//            break;
//        case vneBowl:
//            if (pInfo->v[pInfo->hookPos] != vnl_ab)
//                return processAppend(ev);
//            break;
//        default: //vneHook_uo, vneHookAll
//            if (ev.evType == vneHook_uo && pInfo->v[pInfo->hookPos] == vnl_ab)
//                return processAppend(ev);
//        }
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
//
//        if (!valid)
//            return processAppend(ev);
//
//        changePos = vStart + pInfo->hookPos;
//        if (!m_pCtrl->options.freeMarking && changePos != m_current)
//            return processAppend(ev);
//
//        markChange(changePos);
//        m_buffer[changePos].vnSym = pInfo->v[pInfo->hookPos];
//    }
//   
//    for (i=0; i < pInfo->len; i++) { //update sub-sequences
//        m_buffer[vStart+i].vseq = pInfo->sub[i];
//    }
//
//    //check if tone re-position is needed
//    newTonePos = vStart + getTonePosition(newVs, vEnd == m_current);
//    /* //For now, users don't seem to like the following processing, thus commented out
//    if (hookRemoved && tone != 0 && 
//        (!pInfo->complete || (hookRemoved && curTonePos == changePos))) {
//        //remove tone if the vowel sequence becomes incomplete as a result of hook removal
//        //OR if a removed hook was at the same position as the current tone
//        markChange(curTonePos);
//        m_buffer[curTonePos].tone = 0;
//    }
//    else */
//    if (curTonePos != newTonePos && tone != 0) {
//        markChange(newTonePos);
//        m_buffer[newTonePos].tone = tone;
//        markChange(curTonePos);
//        m_buffer[curTonePos].tone = 0;
//    }
//
//    if (hookRemoved) {
//        m_singleMode = false;
//        processAppend(ev);
//        m_reverted = true;
//    }
//
//    return 1;
//}
////----------------------------------------------------------
//int getTonePosition(int vSeq, boolean terminated)
//{
//    VowelSeqInfo info = VSeqList[vs];
//    if (info.len == 1)
//        return 0;
//
//    if (info.roofPos != -1)
//        return info.roofPos;
//    if (info.hookPos != -1) {
//        if (vs == vs_uhoh || vs == vs_uhohi || vs == vs_uhohu) //u+o+, u+o+u, u+o+i
//            return 1;
//        return info.hookPos;
//    }
//  
//    if (info.len == 3)
//        return 1;
//
//    if (m_pCtrl->options.modernStyle &&
//        (vs == vs_oa || vs == vs_oe ||vs == vs_uy))
//        return 1;
//
//    return terminated ? 0 : 1;
//}
////----------------------------------------------------------
//int UkEngine::processTone(UkKeyEvent & ev)
//{
//    if (m_current < 0 || !m_pCtrl->vietKey)
//        return processAppend(ev);
//
//    if (m_buffer[m_current].form == vnw_c && 
//        (m_buffer[m_current].cseq == cs_gi || m_buffer[m_current].cseq == cs_gin)) {
//        int p = (m_buffer[m_current].cseq == cs_gi)? m_current : m_current - 1;
//        if (m_buffer[p].tone == 0 && ev.tone == 0)
//            return processAppend(ev);
//        markChange(p);
//        if (m_buffer[p].tone == ev.tone) {
//            m_buffer[p].tone = 0;
//            m_singleMode = false;
//            processAppend(ev);
//            m_reverted = true;
//            return 1;
//        }
//        m_buffer[p].tone = ev.tone;
//        return 1;
//    }
//
//    if (m_buffer[m_current].vOffset < 0)
//        return processAppend(ev);
//
//    int vEnd;
//    VowelSeq vs;
//
//    vEnd = m_current - m_buffer[m_current].vOffset;
//    vs = m_buffer[vEnd].vseq;
//    VowelSeqInfo & info = VSeqList[vs];
//    if (m_pCtrl->options.spellCheckEnabled && !m_pCtrl->options.freeMarking && !info.complete)
//        return processAppend(ev);
//
//    if (m_buffer[m_current].form == vnw_vc || m_buffer[m_current].form == vnw_cvc) {
//        ConSeq cs = m_buffer[m_current].cseq;
//        if ((cs == cs_c || cs == cs_ch || cs == cs_p || cs == cs_t) &&
//            (ev.tone == 2 || ev.tone == 3 || ev.tone == 4))
//            return processAppend(ev); // c, ch, p, t suffixes don't allow ` ? ~
//    }
//      
//    int toneOffset = getTonePosition(vs, vEnd == m_current);
//    int tonePos = vEnd - (info.len -1 ) + toneOffset;
//    if (m_buffer[tonePos].tone == 0 && ev.tone == 0)
//        return processAppend(ev);
//
//    if (m_buffer[tonePos].tone == ev.tone) {
//        markChange(tonePos);
//        m_buffer[tonePos].tone = 0;
//        m_singleMode = false;
//        processAppend(ev);
//        m_reverted = true;
//        return 1;
//    }
//
//    markChange(tonePos);
//    m_buffer[tonePos].tone = ev.tone;
//    return 1;
//}
////
//////----------------------------------------------------------
//int UkEngine::processDd(UkKeyEvent & ev)
//{
//    if (!m_pCtrl->vietKey || m_current < 0)
//        return processAppend(ev);
//    
//    int pos;
//
//    // we want to allow dd even in non-vn sequence, because dd is used a lot in abbreviation
//    // we allow dd only if preceding character is not a vowel
//    if (m_buffer[m_current].form == vnw_nonVn && 
//        m_buffer[m_current].vnSym == vnl_d &&
//        (m_buffer[m_current-1].vnSym == vnl_nonVnChar ||!IsVnVowel[m_buffer[m_current-1].vnSym]))
//    {
//        m_singleMode = true;
//        pos = m_current;
//        markChange(pos);
//        m_buffer[pos].cseq = cs_dd;
//        m_buffer[pos].vnSym = vnl_dd;
//        m_buffer[pos].form = vnw_c;
//        m_buffer[pos].c1Offset = 0;
//        m_buffer[pos].c2Offset = -1;
//        m_buffer[pos].vOffset = -1;
//        return 1;
//    }
//
//    if (m_buffer[m_current].c1Offset < 0) {
//        return processAppend(ev);
//    }
//
//    pos = m_current - m_buffer[m_current].c1Offset;
//    if (!m_pCtrl->options.freeMarking && pos != m_current)
//        return processAppend(ev);
//
//    if (m_buffer[pos].cseq == cs_d) {
//        markChange(pos);
//        m_buffer[pos].cseq = cs_dd;
//        m_buffer[pos].vnSym = vnl_dd;
//        //never spellcheck a word which starts with dd, because it's used alot in abbreviation
//        m_singleMode = true;
//        return 1;
//    }
//
//    if (m_buffer[pos].cseq == cs_dd) {
//        //undo dd
//        markChange(pos);
//        m_buffer[pos].cseq = cs_d;
//        m_buffer[pos].vnSym = vnl_d;
//        m_singleMode = false;
//        processAppend(ev);
//        m_reverted = true;
//        return 1;
//    }
//  
//    return processAppend(ev);
//}
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

