package org.geeek.btce.enums;

/**
 * List of all the available pair on BTC-e.
 * 
 * @author Ludovic Toinel
 */
@SuppressWarnings("javadoc")
public enum Pair {
	
	/** BTC */
	btc_usd, btc_rur, btc_eur, 
	
	/** LTC */
	ltc_btc, ltc_usd, ltc_rur, ltc_eur, 
	
	/** NMC */
	nmc_btc, nmc_usd, 
	
	/** NVC */
	nvc_btc, nvc_usd, 
	
	/** USD */
	usd_rur,
	
	/** EUR */
	eur_usd,
	
	eur_rur,
	
	/** TRC */
	trc_btc,
	
	/** PPC */
	ppc_btc, ppc_usd,
	
	/** FTC */
	ftc_btc,
	
	/** XPM */
	xpm_btc;
	
	/**
	 * @return true if it's BTC.
	 */
	public boolean isBtc(){
		return name().startsWith("btc_");
	}
	
	/**
	 * @return true if it's LTC.
	 */
	public boolean isLtc(){
		return name().startsWith("ltc_");
	}
	
	/**
	 * @return true if it's NMC.
	 */
	public boolean isNmc(){
		return name().startsWith("nmc_");
	}
	
	/**
	 * @return true if it's NVC.
	 */
	public boolean isNvc(){
		return name().startsWith("nvc_");
	}

	/**
	 * @return true if it's USD.
	 */
	public boolean isUsd(){
		return name().startsWith("usd_");
	}
	
	/**
	 * @return true if it's EUR.
	 */
	public boolean isEur(){
		return name().startsWith("eur_");
	}
	
	/**
	 * @return true if it's TRC.
	 */
	public boolean isTrc(){
		return name().startsWith("trc_");
	}
	
	/**
	 * @return true if it's PPC.
	 */
	public boolean isPpc(){
		return name().startsWith("ppc_");
	}
	
	/**
	 * @return true if it's FTC.
	 */
	public boolean isFtc(){
		return name().startsWith("ftc_");
	}
	
	public boolean isXpm(){
		return name().startsWith("xpm_");
	}
}
