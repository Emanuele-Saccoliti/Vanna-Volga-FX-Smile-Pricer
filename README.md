## Folder Structure
The project has the following strutural organization:
src/main/java/fxvv/
  Main.java

  conventions/
    DeltaConvention.java

  market/
    SmileQuote.java
    MarketSlice.java
    MarketSliceBuilder.java

  numerics/
    NormalDist.java
    RootFinder.java
    LinearSolver3.java

    impl/
      AbramowitzStegunNormal.java
      BisectionRootFinder.java
      GaussianElimination3.java

  bs/
    GKBlackScholes.java
    GreeksFD.java

  pricer/
    SmilePricer.java
    VannaVolgaSmilePricer.java



Specifically,
    Root package
    - Main.java → application entry point

    market/ = Market data objects and loaders
    - SmileQuote.java → market smile quotes (ATM, RR25, BF25, rates)
    - MarketSlice.java → derived vols and strikes
    - CsvLoader.java → load market data from CSV

    bs/ = Black-Scholes utilities and math
    - Normal.java → Gaussian PDF/CDF
    - GKBlackScholes.java → FX Black-Scholes pricing and Greeks

    vv/ = Core Vanna-Volga engine
    - DeltaStrikeSolver.java → compute strike from delta (bisection)
    - Linear3x3.java → small linear system solver
    - VannaVolgaPricer.java → Vanna-Volga pricing logic

    report/ = Output and reporting tools
    - ReportPrinter.java → console reports
    - CsvExporter.java → export results to CSV
# Vanna-Volga-FX-Smile-Pricer
