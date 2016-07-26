%dw 1.0
%output application/java
---
[{
	address: "????",
	email: "????",
	name: "????",
	type: {
		value: "????"
	} as :object {
		class : "com.google.api.ads.dfp.axis.v201605.CompanyType"
	}
} as :object {
	class : "com.google.api.ads.dfp.axis.v201605.Company"
}]