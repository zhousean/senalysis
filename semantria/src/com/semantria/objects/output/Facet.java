package com.semantria.objects.output;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Facet 
{
	private String label = null;
	private Integer count = null;
	private Integer negative_count = null;
	private Integer positive_count = null;
	private Integer neutral_count = null;
	List<Attribute> attributes = null;
	
	@XmlElement(name="label")
	public String getLabel(){ return label; }
	@XmlElement(name="count")
	public Integer getCount(){ return count; }
	@XmlElement(name="negative_count")
	public Integer getNegativeCount(){ return negative_count; }
	@XmlElement(name="positive_count")
	public Integer getPositiveCount(){ return positive_count; }
	@XmlElement(name="neutral_count")
	public Integer getNeutralCount(){ return neutral_count; }
	@XmlElementWrapper(name="attributes")
	@XmlElement(name="attribute")
	public List<Attribute> getAttributes() { return attributes; }
	
	public void setLabel(String label){ this.label = label; }
	public void setCount(Integer count){ this.count = count; }
	public void setNegativeCount(Integer count){ this.negative_count = count; }
	public void setPositiveCount(Integer count){ this.positive_count = count; }
	public void setNeutralCount(Integer count){ this.neutral_count = count; }
	public void setAttributes(List<Attribute> attributes) { this.attributes = attributes; }
}
