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
			// ��������
			File file = new File("test.xlsx");
			// ���� ���� ����
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));

			Cell cell = null;
			int input = 6;
			int count = 0;
			int num = 0;

			// ù���� sheet ���� �б�
			for (int k = 1; k <= 12; k++) {// �������� ���
				sum = sum + num;
				for (j = 0; j < input; j++) {
					count = sum;
					num = 0;
					for (Row row : wb.getSheetAt(j)) {// sheet 1 = 0
						// ��°�ٺ���..
						if (row.getRowNum() < 1) {// 2���� �о�帲
							continue;
						}
						// �ι�° ���� ��������� for���� �����.
						if (row.getCell(1) == null) {
							break;
						}
						// �ܼ� ���
						// 4��° �� ���� ����
						XSSFCell ccell = (XSSFCell) row.getCell(k);
						data[j][count] = ccell.getNumericCellValue();// double�� ��ȯ
						count++;
						num++;
					}
				}
				if (k == 12)
					sum = sum + num;
			}
			// ����,�ְ�,����,���� 4�� input ����
			// ���� ���� ����

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
					dataSet[i][j] = data[j][i];// data 1000 5 �� change
			}
		}

		answerSet = new double[sum][2];
		
		for(i=0; i<sum; i++) {
			if(ans[i] > 0) {//�񰡿ö�
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
		
		trainer train = new trainer(mormort, dataSet, answerSet);	// �н�������
		error = train.learning(10000);
		//for(double e : error) System.out.println(e);
		
		trainer test = new trainer(mormort, dataSet, answerSet);	// �׽�Ʈ ������
		error = test.test();
		int cnt = 0 ;
		for(i=0; i<sum; i++)
			if(test.guess[i] == answerSet[i][1])
				cnt++;
		System.out.println(cnt + "/1020            ");
	}

}
