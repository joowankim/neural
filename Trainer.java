package neural_network;

import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Trainer {

	double[] inputs;
	static HiddenLayer mormort;

	Trainer(double[] input) {
		int n = input.length;
		inputs = new double[n];

		inputs = input;

		mormort.forwardInput(inputs);
	}

	static Trainer[] training = new Trainer[1100];
	static Trainer[] testing = new Trainer[400];
	static Random random = new Random();
	int count = 0;

	static double f(double x) {
		return x;
	}

	public static void main(String[] args) {

		double[][] Data;
		double[][] data = new double[5][1100];
		int i, j;
		double[] ans;
		double[][] desired;
		int sum = 0;

		try {
			// 엑셀파일
			File file = new File("test.xlsx");
			// 엑셀 파일 오픈
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));

			Cell cell = null;
			int input = 4;
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
						if (j == 0) {// desire answer
							data[4][count] = k;
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

		Data = new double[sum][4];
		ans = new double[sum];

		for (i = 0; i < sum; i++) {
			for (j = 0; j < 5; j++) {
				if (j == 4)
					ans[i] = data[j][i];
				else
					Data[i][j] = data[j][i];// data 1000 5 로 change
			}
		}

		desired = new double[sum][12];

		for (i = 0; i < sum; i++) {
			for (j = 0; j < 12; j++) {
				desired[i][j] = 0;
				if (ans[i] == j + 1)
					desired[i][j] = 1;
			}
		}

		int layerNum = 2; // the number of layer
		int[] eachPtronNum = { 4, 12 }; // the number of each layer's perceptrons
		int cnt = 0;

		mormort = new HiddenLayer(Data[0].length, eachPtronNum, layerNum);

		// training
		for (i = 0; i < sum - 10; i++) {
			// for(j=0;j<4;j++) System.out.println(Data[i][j]);
			training[i] = new Trainer(Data[i]);
			mormort.adjustWeight(desired[i]);

		}
		int temp;
		int max = 0;
	
		// test
		for (i = sum - 10, temp = 0; i < sum; i++, temp++) {
			testing[temp] = new Trainer(Data[i]);

			for (j = 0; j < 4; j++)
				//System.out.println("data[" + j + "]" + Data[i][j]);

			for (j = 0; j < 12; j++) {
				if (mormort.guess[max] < mormort.guess[j])
					max = j;
				System.out.println(j + " : " + mormort.guess[j]);
			}
		
			for (j = 0; j < 12; j++) {
				if (desired[i][j] == 1)
					break;
				// System.out.println(desired[i][j] + " : " + mormort.guess[j]);
			}
			System.out.println("answer   " + j + " : " + desired[i][j]);
			System.out.println("guess   " + max + " : " + mormort.guess[max]);
			if (j == max)
				cnt++;

		}
		System.out.println("        correct answer : " + cnt + "/10");

	}
}
