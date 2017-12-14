package ANN;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class executeMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		double[][] dataSet;
		double[][] answerSet;
		double[][] data = new double[6][1100];
		int i, j;
		double[] ans;
		int sum = 0;

		try {
			// 엑셀파일
			File file = new File("test.xlsx");
			// 엑셀 파일 오픈
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));

			Cell cell = null;
			int input = 6;
			int count = 0;
			int num = 0;

			// 첫번재 sheet 내용 읽기
			for (int k = 1; k <= 12; k++) {// 월단위로 출력
				sum = sum + num;
				for (j = 0; j < input; j++) {
					count = sum;
					num = 0;
					for (Row row : wb.getSheetAt(j)) {// sheet 1 = 0
						// 셋째줄부터..
						if (row.getRowNum() < 1) {// 2부터 읽어드림
							continue;
						}
						// 두번째 셀이 비어있으면 for문을 멈춘다.
						if (row.getCell(1) == null) {
							break;
						}
						// 콘솔 출력
						// 4번째 셀 값을 변경
						XSSFCell ccell = (XSSFCell) row.getCell(k);
						data[j][count] = ccell.getNumericCellValue();// double로 변환
						count++;
						num++;
					}
				}
				if (k == 12)
					sum = sum + num;
			}
			// 최저,최고,습도,일조 4개 input 저장
			// 엑셀 파일 저장

			FileOutputStream fileOut = new FileOutputStream(file);
			wb.write(fileOut);
		} catch (FileNotFoundException fe) {
			System.out.println("FileNotFoundException >> " + fe.toString());
		} catch (IOException ie) {
			System.out.println("IOException >> " + ie.toString());
		}

		dataSet = new double[sum][5];
		ans = new double[sum];

		for (i = 0; i < sum; i++) {
			for (j = 0; j < 6; j++) {
				if (j == 5)
					ans[i] = data[j][i];
				else
					dataSet[i][j] = data[j][i];// data 1000 5 로 change
			}
		}

		answerSet = new double[sum][2];
		
		for(i=0; i<sum; i++) {
			if(ans[i] > 0) {//비가올때
				answerSet[i][0] = 0.99;
				answerSet[i][1] = 0.01;
			}
			else {
				answerSet[i][0] = 0.01;
				answerSet[i][1] = 0.99;
			}
		}

		double[] error;
		
		int[] nodes = { 5, 4, 2 };
		allLayer mormort = new allLayer(nodes, Function.RELU);
		
		trainer train = new trainer(mormort, dataSet, answerSet);	// 학습데이터
		error = train.learning(10000);
		//for(double e : error) System.out.println(e);
		
		trainer test = new trainer(mormort, dataSet, answerSet);	// 테스트 데이터
		error = test.test();
		int cnt = 0 ;
		for(i=0; i<sum; i++)
			if(test.guess[i] == answerSet[i][1])
				cnt++;
		System.out.println(cnt + "/1020            ");
	}

}
