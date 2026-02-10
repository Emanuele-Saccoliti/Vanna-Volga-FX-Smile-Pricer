
# Repository Description

* This repository provides an FX options pricing library based on the Vanna–Volga methodology, calibrated directly to FX smile quotes. The framework transforms market delta quotes into strikes, reconstructing a consistent volatility smile, and producing market-consistent prices for vanilla and first generation exotic FX options.

* More specifically, the library reconstructs the FX volatility smile from sparse market inputs and applies Vanna–Volga adjustments to Black–Scholes prices. Building on the calibrated smile, the framework is extended to exotic payoff foundations, pricing digital options via finite differences on Vanna–Volga adjusted vanilla prices.



# 🔍 Key Objectives

* Reconstruct FX volatility smiles from market quotes

* Apply Vanna–Volga adjustments to obtain smile-consistent vanilla prices

* Build a foundation for exotic option pricing using Vanna-Volga method

* Design a modular, dependency-free numerical architecture for future extension



# 📌 Key Takeaways

* FX options are quoted in delta terms rather than strike terms

* Vanna–Volga bridges Black–Scholes pricing and market smile effects

* Digital options can be priced via finite differences on smile-consistent vanilla prices

$$Digital(K) = - \frac{\partial C(K)}{\partial K} \approx \frac{C(K-\epsilon)-C(K+\epsilon)}{2\epsilon}$$



# ⚠️ Challenges

* Market conventions complexity: FX options rely on multiple delta conventions (spot/forward, premium included/excluded), and incorrect assumptions lead to incorrect strikes and pricing.

* Smile extrapolation risk: Vanna–Volga is most reliable between the 25-delta pillars; far-wing extrapolation may become unstable.

* Finite-difference sensitivity: Greeks and digital prices depend on step-size choices and require adaptive bumping for numerical stability.

* Performance optimization: Repeated evaluations across multiple strikes and maturities require caching and efficient numerical routines to prevent redundant computations.
