package com.example.firstdemo.model;

import java.util.ArrayList;
import java.util.List;

import com.example.firstdemo.dto.MemberApplyBo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 手机客户端使用的model对象
 * 
 * @author user2
 *
 */
public class MemberApplyParcelable implements Parcelable {

	private List<MemberApplyBo> memApplyBos = new ArrayList<MemberApplyBo>();

	public List<MemberApplyBo> getMemApplyBos() {
		return memApplyBos;
	}

	public void setMemApplyBos(List<MemberApplyBo> memApplyBos) {
		this.memApplyBos = memApplyBos;
	}

	public MemberApplyParcelable() {
	}

	public MemberApplyParcelable(List<MemberApplyBo> memApplyBos) {
		this.memApplyBos = memApplyBos;
	}

	@SuppressWarnings("unchecked")
	public MemberApplyParcelable(Parcel in) {
		in.readTypedList(memApplyBos, MemberApplyParcelable.CREATOR);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel outParcel, int flags) {
		outParcel.writeTypedList(memApplyBos);
	}

	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

		@Override
		public MemberApplyParcelable createFromParcel(Parcel in) {
			return new MemberApplyParcelable(in);
		}

		@Override
		public MemberApplyParcelable[] newArray(int size) {
			return new MemberApplyParcelable[size];
		}
	};
}