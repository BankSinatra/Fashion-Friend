package application.clothingModel;

import application.Measurement;

public class Hip {
    Measurement measurement;

    private final double[] femaleSizeKeys = {83.5, 86, 88.5, 93.5, 98.5, 103.5, 108.5, 116, 116.5, 123.5};
    private final int[] femaleSizeValues =  {0, 2, 4, 6, 8, 10, 12, 14, 16, 18};

    public Hip(Measurement measurement){
        this.measurement = measurement;
    }

    public int getHipSize() throws InvalidSizeException {
        double m = measurement.getCentimetersValue();
        if (m < 83.5){
            throw new InvalidSizeException("This calculator does not support sizes this small");
        }else if(m > 123.5){
            throw new InvalidSizeException("This calculator does not support sizes this large");
        }
        int i = 0;
        while(i < femaleSizeKeys.length && m > femaleSizeKeys[i]){
            i++;
        }
        return femaleSizeValues[i];
    }

}
