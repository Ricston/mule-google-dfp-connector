%dw 1.0
%output application/java
---
[{
	address: "????",
	appliedLabels: [{
		isNegated: true,
		labelId: 1
	} as :object {
		class : "com.google.api.ads.dfp.axis.v201605.AppliedLabel"
	}],
	appliedTeamIds: [1],
	comment: "????",
	creditStatus: {
		value: "????"
	} as :object {
		class : "com.google.api.ads.dfp.axis.v201605.CompanyCreditStatus"
	},
	email: "????",
	externalId: "????",
	faxPhone: "????",
	id: 1,
	lastModifiedDateTime: {
		date: {
			day: 1,
			month: 1,
			year: 1
		} as :object {
			class : "com.google.api.ads.dfp.axis.v201605.Date"
		},
		hour: 1,
		minute: 1,
		second: 1,
		timeZoneID: "????"
	} as :object {
		class : "com.google.api.ads.dfp.axis.v201605.DateTime"
	},
	name: "????",
	primaryContactId: 1,
	primaryPhone: "????",
	settings: {
		advertiserDiscount: 1,
		agencyCommission: 1,
		billingCap: {
			value: "????"
		} as :object {
			class : "com.google.api.ads.dfp.axis.v201605.BillingCap"
		},
		billingSchedule: {
			value: "????"
		} as :object {
			class : "com.google.api.ads.dfp.axis.v201605.BillingSchedule"
		},
		billingSource: {
			value: "????"
		} as :object {
			class : "com.google.api.ads.dfp.axis.v201605.BillingSource"
		},
		valueAddedTax: 1
	} as :object {
		class : "com.google.api.ads.dfp.axis.v201605.CompanySettings"
	},
	thirdPartyCompanyId: 1,
	type: {
		value: "????"
	} as :object {
		class : "com.google.api.ads.dfp.axis.v201605.CompanyType"
	}
} as :object {
	class : "com.google.api.ads.dfp.axis.v201605.Company"
}]