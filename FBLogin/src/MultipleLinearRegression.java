/*
 * Project 2 - Facebook webapp
 * @author : Sakti Saurav 2014093
 * 			Himanshu Pahadia 2014045
 */


import Jama.Matrix;
import Jama.QRDecomposition;
import Jama.SingularValueDecomposition;

public class MultipleLinearRegression {
    private final int N;        // number of 
    private final int p;        // number of dependent variables
    private final int Ny;
    //private final Matrix beta;  // regression coefficients
    private double SSE;         // sum of squared
    private double SST;         // sum of squared

    public static Matrix result;
    
    public static double[][] o;
    
    public static double[] ypredicted;
    
    
    public MultipleLinearRegression(double[][] x, double[] y, double[][] xtest, double[] ytest)
    {
        if (x.length != y.length) throw new RuntimeException("dimensions don't agree");
        N = y.length;
        p = x[0].length;
        Ny = ytest.length;
        
        
        Matrix X = new Matrix(x);
        
        
       X.print(X.getColumnDimension(), 2);

        // create matrix from vector
        Matrix Y = new Matrix(y, N);
        Matrix Ytest = new Matrix(ytest, Ny);
        
        
        System.out.println(X.getColumnDimension() + "   "+ X.getRowDimension() + "  " + Y.getColumnDimension() + "  " + Y.getRowDimension() ); 
        
        
       //Y.print(1, 2);

        Matrix XT = X.transpose();
        
        Matrix S = XT.times(X);
        
       // S.print(S.getColumnDimension(), 2);
        
        Matrix I = Matrices.pinv(S);
        
        Matrix P = I.times(XT);
        
        //System.out.println(P.getColumnDimension() + "   "+ P.getRowDimension() + "  " + Y.getColumnDimension() + "  " + Y.getRowDimension() ); 
        Matrix Q = P.times(Y);
        
       // System.out.println(Q.getColumnDimension() + "   "+ Q.getRowDimension()); 
        
        Matrix Theta = Q.transpose();
        
       // Theta.print(Theta.getColumnDimension(), 2);
        
        Matrix X_test = new Matrix(xtest);
        Matrix Y_test = new Matrix(ytest,ytest.length);
        
        
        
        System.out.println(Y_test.getColumnDimension() + " b "+ Y_test.getRowDimension() + "  " + X_test.getColumnDimension() + "  " + X_test.getRowDimension() ); 
        
        
        
        
        Matrix Ynew = Theta.times(X_test.transpose());
        
        
        
        //System.out.println(Ynew.getColumnDimension() + "   "+ Ynew.getRowDimension());
        
        System.out.println("asdvads");
        
       // Ynew.print(Ynew.getColumnDimension(), 2);
        Ytest.print(Ytest.getColumnDimension(), 2);
        
        int sum = 0;
        for(int i=0; i<Ynew.getRowDimension();i++)
        {
        	sum = (int) (sum + (Ynew.get(i, 0) - Y_test.get(i, 0))*(Ynew.get(i, 0) - Y_test.get(i, 0)));
        }
        
        result = Theta;
        
        o = Theta.getArray();
        
        
        ypredicted = Ynew.getColumnPackedCopy();
        
        for(int i=0; i<ypredicted.length;i++)
        {
        	ypredicted[i] = Math.abs(ypredicted[i]);
        	 System.out.print(ypredicted[i]+" ");
        }
        
        
        
        
        
        System.out.println(sum/Ynew.getRowDimension());
        
        
        //Y = (theta') * (x_test)
        
        //Y.print(, d);
            

}
}






class Matrices {
	 /**
	  * The difference between 1 and the smallest exactly representable number
	  * greater than one. Gives an upper bound on the relative error due to
	  * rounding of floating point numbers.
	  */
	 public static double MACHEPS = 2E-16;

	 /**
	  * Updates MACHEPS for the executing machine.
	  */
	 public static void updateMacheps() {
	  MACHEPS = 1;
	  do
	   MACHEPS /= 2;
	  while (1 + MACHEPS / 2 != 1);
	 }

	 /**
	  * Computes the Moore–Penrose pseudoinverse using the SVD method.
	  * 
	  * Modified version of the original implementation by Kim van der Linde.
	  */
	 public static Matrix pinv(Matrix x) {
	  int rows = x.getRowDimension();
	  int cols = x.getColumnDimension();
	  if (rows < cols) {
	   Matrix result = pinv(x.transpose());
	   if (result != null)
	    result = result.transpose();
	   return result;
	  }
	  SingularValueDecomposition svdX = new SingularValueDecomposition(x);
	  if (svdX.rank() < 1)
	   return null;
	  double[] singularValues = svdX.getSingularValues();
	  double tol = Math.max(rows, cols) * singularValues[0] * MACHEPS;
	  double[] singularValueReciprocals = new double[singularValues.length];
	  for (int i = 0; i < singularValues.length; i++)
	   if (Math.abs(singularValues[i]) >= tol)
	    singularValueReciprocals[i] =  1.0 / singularValues[i];
	  double[][] u = svdX.getU().getArray();
	  double[][] v = svdX.getV().getArray();
	  int min = Math.min(cols, u[0].length);
	  double[][] inverse = new double[cols][rows];
	  for (int i = 0; i < cols; i++)
	   for (int j = 0; j < u.length; j++)
	    for (int k = 0; k < min; k++)
	     inverse[i][j] += v[i][k] * singularValueReciprocals[k] * u[j][k];
	  return new Matrix(inverse);
	 }
	}
