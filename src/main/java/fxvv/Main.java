package main.java.fxvv;

import main.java.fxvv.bs.GKBlackScholes;
import main.java.fxvv.conventions.DeltaConvention;
import main.java.fxvv.market.MarketSlice;
import main.java.fxvv.market.MarketSliceBuilder;
import main.java.fxvv.market.SmileQuote;
import main.java.fxvv.numerics.LinearSolver;
import main.java.fxvv.numerics.NormalDist;
import main.java.fxvv.numerics.RootFinder;
import main.java.fxvv.numerics.impl.AbramowitzStegunNormal;
import main.java.fxvv.numerics.impl.BisectionRootFinder;
import main.java.fxvv.numerics.impl.GaussianElimination3;
import main.java.fxvv.pricer.SmilePricer;
import main.java.fxvv.pricer.VannaVolgaPricer;

public class Main {

    public static void main(String[] args) {
        double S = 1.0850;
        double rd = 0.03;
        double rf = 0.02;
        double T = 0.5;

        double sigmaATM = 0.10;
        double rr25 = -0.02;
        double bf25 = 0.01;

        // --- Plug components here ---
        NormalDist normal = new AbramowitzStegunNormal();
        RootFinder rootFinder = new BisectionRootFinder();
        LinearSolver solver3 = new GaussianElimination3();
        DeltaConvention conv = parseDeltaConvention(args);

        GKBlackScholes bs = new GKBlackScholes(normal);
        MarketSliceBuilder builder = new MarketSliceBuilder(bs, rootFinder, conv);

        MarketSlice slice = builder.build(S, rd, rf, new SmileQuote(T, sigmaATM, rr25, bf25));
        SmilePricer pricer = new VannaVolgaPricer(bs, solver3);

        System.out.printf("Delta convention: %s%n", conv);
        System.out.printf("MarketSlice(T=%.4f)%n", slice.T);
        System.out.printf("S=%.6f rd=%.4f rf=%.4f%n", slice.S, slice.rd, slice.rf);
        System.out.printf("sigmaATM=%.4f sigma25P=%.4f sigma25C=%.4f%n", slice.sigmaATM, slice.sigma25P, slice.sigma25C);
        System.out.printf("K_ATM=%.6f K_25P=%.6f K_25C=%.6f%n%n", slice.K_ATM, slice.K_25P, slice.K_25C);

        double K = 1.10;
        double vvCall = pricer.priceVanilla(slice, true, K);
        double vvPut = pricer.priceVanilla(slice, false, K);
        double dCall = pricer.priceDigitalCall(slice, K);
        double dPut = pricer.priceDigitalPut(slice, K);

        System.out.printf("VV Call (K=%.4f): %.8f%n", K, vvCall);
        System.out.printf("VV Put  (K=%.4f): %.8f%n", K, vvPut);
        System.out.printf("VV Digital Call (K=%.4f): %.8f%n", K, dCall);
        System.out.printf("VV Digital Put  (K=%.4f): %.8f%n", K, dPut);
    }

    private static DeltaConvention parseDeltaConvention(String[] args) {
        if (args == null || args.length == 0 || args[0] == null || args[0].trim().isEmpty()) {
            return DeltaConvention.SPOT_PREM_EXCLUDED;
        }

        String raw = args[0].trim();
        String normalized = raw.toUpperCase().replace('-', '_');
        try {
            return DeltaConvention.valueOf(normalized);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    "Unknown delta convention '" + raw + "'. Supported: "
                            + "SPOT_PREM_EXCLUDED, FWD_PREM_EXCLUDED, SPOT_PREM_INCLUDED.",
                    ex
            );
        }
    }
}
