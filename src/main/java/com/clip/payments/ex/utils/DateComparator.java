package com.clip.payments.ex.utils;

import java.util.Comparator;

import com.clip.payments.ex.dtos.Payment;

public class DateComparator implements Comparator<Payment>{

	@Override
	public int compare(Payment o1, Payment o2) {
//        return o1.getDate().compareTo(o2.getDate());
        if(o1.getDate().getTime() > o2.getDate().getTime()){
            return 1;
        } else {
            return -1;
        }
	}

}
