package com.farmacloud.shared.model.DTO;

import java.io.Serializable;

import com.google.gwt.i18n.client.NumberFormat;

public class ProgressUploadDTO implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2062504544265567777L;
	private long theBytesRead = 0;
	private long theContentLength = -1;
	private int percentDone = 0;
	private boolean contentLengthKnown = false;
	
	public ProgressUploadDTO(){
	}

	public long getTheBytesRead() {
		return theBytesRead;
	}

	public void setTheBytesRead(long theBytesRead) {
		this.theBytesRead = theBytesRead;
	}

	public long getTheContentLength() {
		return theContentLength;
	}

	public void setTheContentLength(long theContentLength) {
		this.theContentLength = theContentLength;
	}

	public int getPercentDone() {
		return percentDone;
	}

	public void setPercentDone(int percentDone) {
		this.percentDone = percentDone;
	}

	public boolean isContentLengthKnown() {
		return contentLengthKnown;
	}

	public void setContentLengthKnown(boolean contentLengthKnown) {
		this.contentLengthKnown = contentLengthKnown;
	}
	
	public String getKbRead(){
		double kb = theBytesRead/1000.0;
		String formattedKb = NumberFormat.getFormat("0.00").format(kb);
		return formattedKb;
	}
	
	public String getMbRead(){
		double mb = theBytesRead/1000000.0;
		String formattedMb = NumberFormat.getFormat("0.0").format(mb);
		return formattedMb;
	}
	
	public String getLengthInKb(){
		double kb = theContentLength/1000.0;
		String formattedKb = NumberFormat.getFormat("0.00").format(kb);
		return formattedKb;
	}
	
	public String getLengthInMb(){
		double mb = theContentLength/1000000.0;
		String formattedKb = NumberFormat.getFormat("0.00").format(mb);
		return formattedKb;
	}
	
	public String getProgressInfo()
	{
		String info = "";
		String kbRead = getKbRead();
		String mbRead = getMbRead();
		
		if(isContentLengthKnown())
		{
			String kbTotal = getLengthInKb();
			String mbTotal = getLengthInMb();
			long contentKb = theContentLength/1000;
			
			if(theBytesRead<=1000)
				info = theBytesRead+" bytes de "+theContentLength;
			else if(contentKb<=1000)
					info = kbRead+" KBs de "+kbTotal+" KBs";
				 else
					info = mbRead+" MBs de "+mbTotal+" MBs";
		}
		else
		{
			long kbReadLong = theBytesRead/1000;
			
			if(theBytesRead<1000)
				info = theBytesRead+" bytes";
			else if(kbReadLong<=1000)
					info = kbRead+" KBs";
				 else
					info = mbRead+" MBs";
		}
		
		return info;
	}
}
