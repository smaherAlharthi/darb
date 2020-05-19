package classes;

import java.text.DecimalFormat;

public class ConvertToFormat {

	DecimalFormat df = new DecimalFormat("#.##");

	public String convertToFormat(double value) {

		return df.format(value);
	}
}
