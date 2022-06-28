package com.dodol.excel;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelVO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@CellOrderAnnotation2(order = 0)
	private String no;
	@CellOrderAnnotation2(order = 1)
	private String name;
	@CellOrderAnnotation2(order = 2)
    private String age;
	@CellOrderAnnotation2(order = 3)
    private String address;
	
    @CellOrderAnnotation(order = 0)
    public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	@CellOrderAnnotation(order = 1)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@CellOrderAnnotation(order = 2)
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	@CellOrderAnnotation(order = 3)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
