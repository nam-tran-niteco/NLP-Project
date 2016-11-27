/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainProcess;

import CheckVietnameseSpell.InvalidVietnameseRule;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tran
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        InvalidVietnameseRule invalidVietnameseRule = new InvalidVietnameseRule();
        String vowelSeq = "ch";
        int[] subIndex = new int[3];
        for(int i = 0; i < vowelSeq.length(); i++) {
            subIndex[i] = invalidVietnameseRule.getLexPos(vowelSeq.charAt(i));
            System.out.println(subIndex[i]);
        }
        int pos = invalidVietnameseRule.binarySearchConSeq(subIndex, invalidVietnameseRule.getCSeqCount());
        System.out.println(pos);
//        InvalidVietnameseRule.VowelSeqInfo[] list = invalidVietnameseRule.getVSeqList();
//        System.out.println(list[2].vSeq[0]);
//        String test = "BÁN CHÓ CON\n" +
//"Chủ một cửa hàng bách hóa đính kèm trên bảng hiệu ở cửa hàng dòng chữ “Bán chó con”. Bảng hiệu liền thu hút trẻ nhỏ ngay, và đúng như vậy, một cậu bé đã đến dưới bảng hiệu của chủ cửa hàng bách hóa. “Ông định bán những con chó nhỏ với giá bao nhiêu?”, cậu bé hỏi. Chủ cửa hàng bách hóa trả lời: “Bất cứ giá nào từ 30 đô la đến 50 đô la”.\n" +
//"Cậu bé lấy trong túi và đặt ra ngoài một ít tiền lẻ. “Cháu có 2 đô la 37 xu”, cậu bé nói. “Làm ơn cho cháu nhìn chúng được không?”.\n" +
//"Chủ cửa hàng bách hóa mỉm cười, huýt gió và đi xuống chuồng chó. Ở đằng sau là một con chó đang bị cách ly. Ngay lập tức cậu bé phát hiện ra con chó khập khiễng, đi chậm phía sau và nói: “Có điều gì không bình thường với con chó nhỏ này à?”.\n" +
//"Chủ cửa hàng bách hóa giải thích rằng bác sĩ thú y đã khám bệnh cho con chó nhỏ này và phát hiện ra nó không có cái hông. Nó sẽ luôn luôn đi khập khiễng và luôn luôn bị tật.\n" +
//"Cậu bé cảm thấy bị kích động và nói: “Đó là con chó nhỏ mà cháu muốn mua”.\n" +
//"“Không, cháu không nên mua con chó nhỏ đó. Nếu cháu thật muốn nó, chú sẽ tặng nó cho cháu”, chủ cửa hàng bách hóa nói.\n" +
//"Cậu bé nhận được kết quả hết sức bất ngờ. Cậu nhìn thẳng vào trong đôi mắt của ông chủ cửa hàng bách hóa, chỉ ngón tay về phía con chó và nói: “Cháu không muốn chú tặng con chó cho cháu. Con chó nhỏ đó trị giá nhiều như tất cả con chó khác và cháu sẽ trả giá đầy đủ. Nói tóm lại, cháu sẽ đưa cho chú 2 đô la 37 xu bây giờ, và 50 xu mỗi tháng cho đến khi cháu trả hết cho chú”.\n" +
//"Chủ cửa hàng bách hóa lưỡng lự và khuyên: “Thật ra ch*( không nên mua con chó nhỏ này. Nó không thể nhảy và chơi với cháu như những con chó khác”.\n" +
//"Đến đây, cậu bé cúi xuống và ôm con chó đang thở hổn hển, cái chân bị xoắn rất xấu, chân trái bị tật được chống giữ bởi một thanh chống kim loại lớn. Cậu bé nhìn chủ cửa hàng bách hóa và trả lời một cách nhẹ nhàng: “Tốt, cháu không đi dạo một mình, và con chó nhỏ sẽ cần một người nào đó quan tâm đến!”";
//        String buffer = "";
//        int length = test.length();
//        int i;
//        for (i = 0; i < length; i++) {
//            char key = test.charAt(i);
//            if( key != '“' && key != '”' && key != '.' && key != ',' && key != ':' ) buffer += key;
//        }
//        String test1[] = buffer.split(" ");
//        List hashTable = new ArrayList();
//        int arr_length = test1.length;
//        for ( i = 0; i < arr_length; i++ ) {
////            System.out.println(test1[i] + " " + test1[i].hashCode());
//            int size = hashTable.size();
//            int low = 0;
//            int high = size - 1;
//            int key = test1[i].hashCode();
//            boolean found = false;
//            while (high > low) {
//                int middle = (low + high) / 2;
//                if ((int)hashTable.get(middle) == key) {
//                    found = true;
//                    break;
//                }
//                if ((int)hashTable.get(middle) > key) {
//                    low = middle + 1;
//                }
//                if ((int)hashTable.get(middle) < key) {
//                    high = middle - 1;
//                }
//            }
//            if(!found){
//                hashTable.add(low, key);
//                System.out.println(key);
//            }
//        }
//        int arr_length = test1.length;
//        for (int j = 0; j < arr_length; j++) {
//            int length = test1[j].length();
//            for ( int i = 0; i < length; i++) {
//                if(Arrays.binarySearch(invalidVietnameseRule.UnicodeTable, test1[j].charAt(i)) == -1) {
//                    System.out.println(test1[j]);
//                }
//            }
//        }        
//        int length = invalidVietnameseRule.UnicodeTable.length;
//        int i, j;
//        float key;
//        for (i = 1; i < length; i ++) {
//            key = invalidVietnameseRule.UnicodeTable[i];
//            for (j = i - 1; (j >= 0) && ((float)invalidVietnameseRule.UnicodeTable[j] < key); j--){
//                invalidVietnameseRule.UnicodeTable[j+1] = invalidVietnameseRule.UnicodeTable[j];
//            }
//            invalidVietnameseRule.UnicodeTable[j+1] = (char)key;
//        }
        
//        for ( i = 1; i < length; i++ ){
////            if (((float)invalidVietnameseRule.UnicodeTable[i-1] - (float)invalidVietnameseRule.UnicodeTable[i]) > 1.0 ){
//////                System.out.println((float)invalidVietnameseRule.UnicodeTable[i-1]);
////            }
//            System.out.println((float)invalidVietnameseRule.UnicodeTable[i] + " " + invalidVietnameseRule.UnicodeTable[i]);
//        }
    }
    
}
