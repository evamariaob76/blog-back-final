package com.eva.blog.backend.auth;

public class JwtConfig {
	public static final String LLAVE_SECRETA="alguna.clase.secreta.12345678";
	
	public static final  String RSA_PRIVADA="-----BEGIN RSA PRIVATE KEY-----\r\n" + 
			"MIIEowIBAAKCAQEAp6fd+7eQLkjJ+whSjXDNLTIi1ZNk38NY1RXnTKuUNKOc3wa3\r\n" + 
			"N4WBCUp0tiurp82q16CbD2+Rh0IhJnFQRbJhraP/ukFWCagbzz6txe9WTnRX7g2c\r\n" + 
			"8nwzxZYOm0jTlfNAZtiuRQmIjF7MD7uuIOQJ9pqK8vDziJe6R5jC7DWeteuPehbu\r\n" + 
			"rXEhZWqxZTE2Gc1i9BDheBp0NmlON4xe5FKF3hbjx0ZStedNMrd3GJQw+dn/5+ah\r\n" + 
			"LQFnxf28wq68BB2P3unnM6E9fMlw0X2dMWPxwXaZnY85GzbQEcKyNyrs/vXHxlUC\r\n" + 
			"tveHsF8kaFto5pYqdhA27VlQqstMMFbe/tPOhwIDAQABAoIBABWlEX6m80FWemUj\r\n" + 
			"bOz/zC1ylJa4QcS1xqkz+7mlSSwGl4Efr9XGMaOObkT6nF28qrrN7Fbw03v/6KG6\r\n" + 
			"7V8iKYQucRyNxNrYTkbSU8MDJERatuNv9HxF1rVCqz4XB+ILFpxwiwc0tOFSd8vs\r\n" + 
			"Mg3jAvpId3nn67kNxanqqfeu8H+R5VfsDslNwHKy+05MSEHXL7/ThEVsX2ftATSc\r\n" + 
			"0EQxSbutS+VUgfTN4gpxCeyNyvFoNoBW8rlESmImuxBpz2fD15xyKYWVnwHDLoTi\r\n" + 
			"DeSbNgKM2MWl7cMBSOrRaJ/7vffhrVvdsc8hh4uf710s46f9SIK07URvLCVQ+BoN\r\n" + 
			"ee6jcSkCgYEA2Fm8pYUaWAn/vCZ010lldYq6z3ap7PvgYihvUuIUt0mSGIvhKsdB\r\n" + 
			"lujk7BIxI3YAqH8Mp1zhq0dSx9XpquiZTktc/exBw6y1cLwOQP1sY4b68U0TShO4\r\n" + 
			"nU3My0+AH6ydryJpgBUuWMffrlSH3MWBAGHiTXrefUayhH2pFpigadUCgYEAxmGS\r\n" + 
			"0E1jDEVRTOO8oDmQZIfjH4B3jYsQrc2EUN9PBm9FqVsm+GPOI6+aUMNoXObnadls\r\n" + 
			"FRkKqHtdy9UNch9hyFw33HzsLkfy1Knqmf86EidlQghU3DY1IZtgc60rmvS9x+4L\r\n" + 
			"9H4K8Ln6qnpy2lDMiKFu/o4msfkbP5qMBF00COsCgYAZEOSLZM11j8sCWI5G9yj7\r\n" + 
			"IlScfsoXO+N2W+rx9mGiDrMPhv1SgH1354nGVCE/U4el/fpQVbGLr5rGrmEBPSut\r\n" + 
			"54thnALOke7/nbolC3eIXHYsiNqIRZqpFv94OsDEarJmPR1uCoYxIQ8oWoDXMkbM\r\n" + 
			"7YpTob2vVAaQ1SJHDO3g9QKBgQCE52W2S8IpvF7FRmVeanJEB42v0x9FIn+gEOmu\r\n" + 
			"TIzi4LUoyrqG+kZmPgUCSoemhcNPdKwmTkqHqW6jWi7R4BhgZPe/ramFMAqNUvCr\r\n" + 
			"DwRWmTILVQ2VInPh+fhaRw/JlmF2ihJUUAUAVXiLsdE0VaFRf+WegJmH7j3FFMLt\r\n" + 
			"QYvU1QKBgCY3cdO4OsifQICrMbSKeVe45vN/kDA1+km2hru1uN3eTke49+v59nmz\r\n" + 
			"cZlWQSHfgISptH5x+XsxKquapQ+dyCe7G0aHvHrd8Z1FYXAs6GFS5q+SLGL3zhtB\r\n" + 
			"NDFbAYF2HN2KXWUluDV/G2uMsnYGwKuE06cuygkNtnMoI7IQlHUh\r\n" + 
			"-----END RSA PRIVATE KEY-----";

	
	public static final  String RSA_PUBLICA="-----BEGIN PUBLIC KEY-----\r\n" + 
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp6fd+7eQLkjJ+whSjXDN\r\n" + 
			"LTIi1ZNk38NY1RXnTKuUNKOc3wa3N4WBCUp0tiurp82q16CbD2+Rh0IhJnFQRbJh\r\n" + 
			"raP/ukFWCagbzz6txe9WTnRX7g2c8nwzxZYOm0jTlfNAZtiuRQmIjF7MD7uuIOQJ\r\n" + 
			"9pqK8vDziJe6R5jC7DWeteuPehburXEhZWqxZTE2Gc1i9BDheBp0NmlON4xe5FKF\r\n" + 
			"3hbjx0ZStedNMrd3GJQw+dn/5+ahLQFnxf28wq68BB2P3unnM6E9fMlw0X2dMWPx\r\n" + 
			"wXaZnY85GzbQEcKyNyrs/vXHxlUCtveHsF8kaFto5pYqdhA27VlQqstMMFbe/tPO\r\n" + 
			"hwIDAQAB\r\n" + 
			"-----END PUBLIC KEY-----";
}
