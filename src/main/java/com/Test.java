package com;


import org.json.simple.parser.ParseException;

public class Test {

	public static void main(String[] args) throws ParseException {
		String json = "{\"meta\":{\"status\":\"OK\",\"timestamp\":\"2020-05-28T14:16:04.618+0000\"},\"links\":{\"data.createdBy\":{\"type\":\"users\",\"href\":\"https://jama.verifone.com/contour/rest/latest/users/{data.createdBy}\"},\"data.modifiedBy\":{\"type\":\"users\",\"href\":\"https://jama.verifone.com/contour/rest/latest/users/{data.modifiedBy}\"},\"data.fields.status\":{\"type\":\"picklistoptions\",\"href\":\"https://jama.verifone.com/contour/rest/latest/picklistoptions/{data.fields.status}\"},\"data.location.parent.item\":{\"type\":\"items\",\"href\":\"https://jama.verifone.com/contour/rest/latest/items/{data.location.parent.item}\"},\"data.fields.lookup1\":{\"type\":\"picklistoptions\",\"href\":\"https://jama.verifone.com/contour/rest/latest/picklistoptions/{data.fields.lookup1}\"},\"data.project\":{\"type\":\"projects\",\"href\":\"https://jama.verifone.com/contour/rest/latest/projects/{data.project}\"},\"data.fields.lookup2\":{\"type\":\"picklistoptions\",\"href\":\"https://jama.verifone.com/contour/rest/latest/picklistoptions/{data.fields.lookup2}\"},\"data.itemType\":{\"type\":\"itemtypes\",\"href\":\"https://jama.verifone.com/contour/rest/latest/itemtypes/{data.itemType}\"},\"data.fields.engineering_severity$26\":{\"type\":\"picklistoptions\",\"href\":\"https://jama.verifone.com/contour/rest/latest/picklistoptions/{data.fields.engineering_severity$26}\"}},\"data\":{\"id\":3996727,\"documentKey\":\"PETROCOMM-TC-17884\",\"globalId\":\"GID-3846692\",\"itemType\":26,\"project\":56,\"createdDate\":\"2018-06-13T12:36:42.000+0000\",\"modifiedDate\":\"2020-05-22T05:00:16.000+0000\",\"lastActivityDate\":\"2020-05-22T05:00:16.000+0000\",\"createdBy\":3856,\"modifiedBy\":5416,\"fields\":{\"documentKey\":\"PETROCOMM-TC-17884\",\"globalId\":\"GID-3846692\",\"name\":\"TC-1 PLU item Sale and Tender with Cash as MOP \",\"description\":\"<p>1. Verify that PLU item Sale and Tender with as Cash as MOP is successful</p>\\n\\n<p>&nbsp;</p>\\n\\n<p><img alt=\\\"\\\" height=\\\"382\\\" src=\\\"https://jama.verifone.com/contour/attachment/44171/M_49-1.jpg\\\" width=\\\"359\\\" /></p>\\n\",\"status\":292,\"testCaseStatus\":\"SCHEDULED\",\"lookup1\":340,\"lookup2\":369,\"testCaseSteps\":[{\"action\":\"Ring up PLU 9111 and Tender with Cash as MOP\",\"expectedResult\":\"Transaction should be completed successful and Receipt and t-log validation should  be successful.\",\"notes\":\"\"}],\"engineering_severity$26\":741},\"resources\":{\"self\":{\"allowed\":[\"GET\",\"PUT\",\"PATCH\",\"DELETE\"]}},\"location\":{\"sortOrder\":1,\"globalSortOrder\":20982969,\"sequence\":\"3.2.1.1.1.2\",\"parent\":{\"item\":3996698}},\"lock\":{\"locked\":false,\"lastLockedDate\":\"2020-05-26T08:54:08.000+0000\"},\"type\":\"items\"}}";
		String status = "FAILED", currentStatus = null;
		
		Initializer.wsp.updateTestResultsInJAMA(json, status);

	}

}
