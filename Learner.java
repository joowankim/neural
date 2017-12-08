package ai;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Learner {

	LayerSet layerSet;
	double[][] dataSet;
	double[][] answerSet;

	/**
	 * 레이어셋을 학습시키는 클래스
	 * 
	 * @param layerSet
	 *            학습시킬 레이어셋
	 * @param dataSet
	 *            학습시킬 데이터셋
	 * @param answerSet
	 *            '정답'
	 */
	public Learner(LayerSet layerSet, double[][] dataSet, double[][] answerSet) {

		this.layerSet = layerSet;
		this.dataSet = dataSet;
		this.answerSet = answerSet;

		// 가중치 임의 초기화
		layerSet.randomize(-1, 1);
	}

	/**
	 * AI를 테스트할 클래스
	 * 
	 * @param layerSet
	 *            테스트할 AI
	 * @param dataSet
	 *            테스트할 데이터셋
	 */

	public Learner(LayerSet layerSet, double[][] dataSet) {

		this.layerSet = layerSet;
		this.dataSet = dataSet;

	}

	/**
	 * 
	 * @param count
	 *            학습할 횟수
	 * @return 오차
	 */
	public double[] scgLearn(int count) {
		if (dataSet.length == answerSet.length) {
			double[] output = new double[dataSet.length * count];
			int k = 0;
			for (int c = 0; c < count; c++) {
				for (int i = 0; i < dataSet.length; i++) {
					double[] result = layerSet.calc(dataSet[i], answerSet[i]);
					double[] err = getErr(result, answerSet[i]);
					output[k] = CEE(result, answerSet[i]);
					k++;
					double t = 0;

					for (double g : result) {
						t += g;
						//System.out.println(g);
					}

					System.out.println(t);
					
					layerSet.backProp(err, answerSet[i]);
					//layerSet.printWeight();
				}

			}
			return output;
		} else {
			return null;
		}
	}

	public void scgTest(double[][] answerSet) {
		this.answerSet = answerSet;
		int cnt = 0;
		if (dataSet.length == answerSet.length) {
			for (int i = 0; i < dataSet.length; i++) {
				double[] guess = layerSet.calc(dataSet[i], answerSet[i]);

				double t = 0;
				for (double g : guess) {
					t += g;
					//System.out.println(g);
				}

				//System.out.println(t);

				int output = maxIndex(guess);
				for (int j = 0; j < 12; j++) {
					// if (answerSet[i][j] == 1) System.out.println("answer : " + j);
					if (answerSet[i][j] == 1 && j == output)
						cnt++;
				}
				// System.out.println("guess>>>" + output);
			}
		} else {
			System.out.println("data set과 answer set의 개수가 다릅니다");
			return;
		}

		System.out.println("      SCORE : " + cnt + "/" + answerSet.length);

	}

	public int maxIndex(double[] guess) {
		int m = 0;
		for (int i = 0; i < guess.length; i++)
			if (guess[m] < guess[i])
				m = i;
		return m;
	}

	public LayerSet getLayerSet() {
		return this.layerSet;
	}

	/**
	 * answer-result의 벡터를 배열로 구함.
	 * 
	 * @param result
	 * @param answer
	 * @return
	 */
	protected double[] getErr(double[] result, double[] answer) {
		double[] output = new double[result.length];
		for (int i = 0; i < answer.length; i++) {
			output[i] = answer[i] - result[i];
		}
		return output;
	}

	/**
	 * 배열을 벡터로 생각하여 노름(벡터의 크기)을 구함.
	 * 
	 * @param d
	 * @return
	 */
	private double CEE(double[] g, double[] ans) {
		double output = 0;
		for (int i = 0; i < g.length; i++) {
			output += -Math.log(g[i]) * ans[i];
		}
		return output;
	}

	public static void main(String[] args) {

		double[][] dataSet;
		double[][] answerSet;
		double[][] data = new double[5][1100];
		int i, j;
		double[] ans;
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

		dataSet = new double[sum][4];
		ans = new double[sum];

		for (i = 0; i < sum; i++) {
			for (j = 0; j < 5; j++) {
				if (j == 4)
					ans[i] = data[j][i];
				else
					dataSet[i][j] = data[j][i];// data 1000 5 로 change
			}
		}

		answerSet = new double[sum][12];

		for (i = 0; i < sum; i++) {
			for (j = 0; j < 12; j++) {
				answerSet[i][j] = 0;
				if (ans[i] == j + 1)
					answerSet[i][j] = 1;
			}
		}

		int[] nodes = { 4, 4, 12 };
		LayerSet AI = new LayerSet(nodes, Function.SIGMOID);
		AI.randomize(0, 1);
		AI.setLearningRate(0.01);

		Learner learner = new Learner(AI, dataSet, answerSet);
		double[] loss = learner.scgLearn(10);
		/*
		 * for(double e : loss) System.out.println("Loss : " + e);
		 */

		Learner tester = new Learner(AI, dataSet);
		tester.scgTest(answerSet);

	}

}
