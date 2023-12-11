public class T_distribution {

    private static final double NEG_INF = -20.0; // representing - infinity
    private static final double N = 500.0; // number of intervals for simpsons rule to approximate integral

    public T_distribution() {
    }

    public static double pdf(double x, double df) {

        double constant = 1;
        
        if(df % 2 == 0) {
        
            for (double i =  df-1; i >= 3; i -= 2) {
                constant *= i / (i-1); // this is necessary otherwise we end up with Inf / Inf if we use the traditional gamma function. 
                //see wikipedia
            }
            constant = constant / (2 * Math.sqrt(df));
        } else {
            for (double i =  df-1; i > 1; i -= 2) {
                constant *= i / (i-1); // this is necessary otherwise we end up with Inf / Inf if we use the traditional gamma function.
                // see wikipedia
            }
            constant = constant / (Math.PI * Math.sqrt(df));
        } 

        double eval = Math.pow(1 + x*x / df, -0.5*(df + 1));
        return constant * eval;
    }

    public static double cdf(double b, double df) {
        if(df == 1) return 0.5 + (1/Math.PI) * Math.atan(b);

        double step = (b - NEG_INF) / N;
    
        int multiplier;

        double approx = 0;
        double position = NEG_INF;

        for (int i = 0; i < N+1; i++) { // Simpsons rule
            if (i == 0 | i == N) multiplier = 1; 
            else if (i % 2 != 0) multiplier = 4;
            else multiplier = 2;

            approx = approx + multiplier * T_distribution.pdf(position, df);
            position += step;
        }
        
        return approx * step/3;
    }

}
