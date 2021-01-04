package nic.vahan5.reg.server;

import java.sql.PreparedStatement;
import java.util.Date;

import javax.sql.RowSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.vahan5.reg.db.TableList;
import nic.vahan5.reg.db.connection.TransactionManagerOne;
import nic.vahan5.reg.db.connection.TransactionManagerReadOnly;
import nic.vahan5.reg.form.dobj.AxleDetailsDobj;
import nic.vahan5.reg.form.dobj.OwnerDetailsDobj;
//import nic.vahan5.reg.CommonUtils.FormulaUtilities;
import nic.vahan5.reg.common.VahanException.VahanException;
import nic.vahan5.reg.form.dobj.Owner_dobj;
//import nic.vahan5.reg.form.dobj.Status_dobj;

@Service
public class ServerUtil {

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(ServerUtil.class);
    
    @Autowired
    TransactionManagerReadOnly tmgr;

    public Owner_dobj getOnwerDobjFromHomologation(String chasiNo, String engineNo, String stateCd, int offCd, boolean homoEngValidate) throws VahanException {
        Owner_dobj owner_dobj = null;
//        TransactionManagerReadOnly tmgr = null;
        String sql = "SELECT * FROM gethomologationchassisinfo(?) ";
        try {
            PreparedStatement ps = tmgr.prepareStatement(sql);
            ps.setString(1, chasiNo.toUpperCase().trim());
            RowSet rs = tmgr.fetchDetachedRowSet_No_release();

            if (rs.next()) {
                owner_dobj = new Owner_dobj();
                owner_dobj.setOwnerDetailsDo(new OwnerDetailsDobj());
                String engnNo = rs.getString("eng_no");
                if (engnNo.length() > 5) {
                    engnNo = engnNo.substring(engnNo.length() - 5);
                }
                if (engnNo != null && !engnNo.equals("") && !engnNo.equals(engineNo) && homoEngValidate) {
                    throw new VahanException("Chassis Details Found on Homologation Portal but Chassis No and Engine No combination is not valid!!!");
                }
                owner_dobj.setEng_no(rs.getString("eng_no"));
                owner_dobj.setChasi_no(rs.getString("chasi_no"));
                owner_dobj.setMaker(rs.getInt("maker_code"));
                owner_dobj.setMaker_model(rs.getString("unique_model_ref_no"));
                owner_dobj.setVch_purchase_as(rs.getString("vehicle_make_as"));
                owner_dobj.setHp(rs.getFloat("engine_power") * 1.34f);  //Metric HP Conversion-> 1 kw = 1.36 BHP and British Mechanic HP-> 1 kw = 1.34 BHP
                owner_dobj.setSeat_cap(rs.getInt("seat_cap"));
                owner_dobj.setUnld_wt(rs.getInt("unld_wt"));
                owner_dobj.setLd_wt(rs.getInt("gvw"));
                owner_dobj.setGcw(rs.getInt("gcw"));
                owner_dobj.setFuel(rs.getInt("fuel"));
                owner_dobj.setBody_type(rs.getString("body_type"));
                owner_dobj.setNo_cyl(rs.getInt("no_cyl"));
                owner_dobj.setWheelbase(rs.getInt("wheelbase"));
                owner_dobj.setNorms(rs.getInt("norms"));
                owner_dobj.setDealer_cd(rs.getString("dealer_cd"));
                owner_dobj.setCubic_cap(rs.getFloat("cubic_cap"));
                owner_dobj.setVch_catg(rs.getString("vch_catg"));
                owner_dobj.setLength(rs.getInt("length"));
                owner_dobj.setWidth(rs.getInt("width"));
                owner_dobj.setHeight(rs.getInt("height"));
                owner_dobj.setRegn_dt(new Date());
                owner_dobj.setFeatureCode(rs.getString("feature_cd"));
                owner_dobj.setColorCode(rs.getString("color_cd"));
                owner_dobj.setHomoVchClass(rs.getString("vch_class"));
                owner_dobj.setC_state(stateCd);
                owner_dobj.setModelManuLocCode(rs.getString("model_manu_loc"));
                owner_dobj.setModelNameOnTAC(rs.getString("model_name_on_tac"));
                if (!CommonUtils.isNullOrBlank(owner_dobj.getModelManuLocCode())) {
                    switch (owner_dobj.getModelManuLocCode()) {
                        case "M":
                            owner_dobj.setModelManuLocCodeDescr("Manufactured in India");
                            break;
                        case "A":
                            owner_dobj.setModelManuLocCodeDescr("Assembled in India(Imported)");
                            break;
                        case "I":
                            owner_dobj.setModelManuLocCodeDescr("Fully Built Imported");
                            break;
                    }
                }
                int month = 0;
                int year = 0;
                String monthYear = String.format("%06d", rs.getInt("month_year"));
                if (monthYear.length() == 6) {
                    month = Integer.parseInt(monthYear.substring(0, 2));
                    year = Integer.parseInt(monthYear.substring(2, 6));
                }
                owner_dobj.setManu_mon(month);
                owner_dobj.setManu_yr(year);

                if (rs.getString("color_name") != null && rs.getString("color_name").length() > 20) {
                    owner_dobj.setColor(rs.getString("color_name").substring(0, 20));
                } else {
                    owner_dobj.setColor(rs.getString("color_name"));
                }
                owner_dobj.getOwnerDetailsDo().setMaker_name(rs.getString("maker_descr"));
                owner_dobj.getOwnerDetailsDo().setModel_name(rs.getString("model_name"));
                owner_dobj.getOwnerDetailsDo().setFuel_descr(rs.getString("fuel_descr"));
                owner_dobj.getOwnerDetailsDo().setModelNameOnTAC(rs.getString("model_name_on_tac"));


                //-----------Filling Axle Details Start-------------------------
                AxleDetailsDobj axleDobj = new AxleDetailsDobj();
                owner_dobj.setAxleDobj(axleDobj);

                owner_dobj.getAxleDobj().setTf_Front(rs.getInt("f_axle_weight"));
                owner_dobj.getAxleDobj().setTf_Front1(rs.getString("f_axle_descp"));
                owner_dobj.getAxleDobj().setTf_Rear(rs.getInt("r_axle_weight"));
                owner_dobj.getAxleDobj().setTf_Rear1(rs.getString("r_axle_descp"));
                owner_dobj.getAxleDobj().setTf_Other(rs.getInt("o1_axle_weight"));
                owner_dobj.getAxleDobj().setTf_Other1(rs.getString("o1_axle_descp"));
                owner_dobj.getAxleDobj().setTf_Tandem(rs.getInt("t_axle_weight"));
                owner_dobj.getAxleDobj().setTf_Tandem1(rs.getString("t_axle_descp"));

                owner_dobj.setPurchase_dt(new Date());
                owner_dobj.setOwner_sr(1);

                String showroomPriceSql = "SELECT gethomologationshowroomprice(?,?,?,?,?,?) as salePrice";

                ps = tmgr.prepareStatement(showroomPriceSql);
                ps.setInt(1, rs.getInt("maker_code"));
                ps.setString(2, rs.getString("unique_model_ref_no"));
                ps.setString(3, stateCd);
                ps.setInt(4, offCd);
                ps.setString(5, rs.getString("color_cd"));
                ps.setString(6, rs.getString("feature_cd"));

                RowSet rs1 = tmgr.fetchDetachedRowSet();
                if (rs1.next()) {
                    owner_dobj.setSale_amt(rs1.getInt("salePrice"));
                }
            }
            if (owner_dobj != null) {
//                validateHomoData(owner_dobj.getVch_purchase_as(), owner_dobj.getNorms(), owner_dobj.getFuel(), owner_dobj.getSeat_cap(), owner_dobj.getNo_cyl(), owner_dobj.getManu_mon(), owner_dobj.getManu_yr(), owner_dobj.getLd_wt(), owner_dobj.getUnld_wt(), owner_dobj.getHomoVchClass());
            	populateHomoDataIfEmpty(owner_dobj);
            }

        } catch (VahanException vex) {
            throw vex;
        } catch (Exception ex) {
            LOGGER.error(ex.toString() + " " + ex.getStackTrace()[0]);
//            throw new VahanException("Error in getting homologation details");
            throw new VahanException(ex.getMessage());
        } finally {
            if (tmgr != null) {
                try {
                    tmgr.release();
                } catch (Exception e) {
                    LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
                }
            }
        }
        return owner_dobj;
    }//end of getOnwerDobjFromHomologation

    /*
	 * Created to populate empty Homologation data
	 * FOR TESTING ONLY
	 */
	private void populateHomoDataIfEmpty(Owner_dobj owner_dobj) {

		if (CommonUtils.isNullOrBlank(owner_dobj.getVch_purchase_as())) {
			owner_dobj.setVch_purchase_as("B");
		}
		if (owner_dobj.getFuel() <= 0) {
			owner_dobj.setFuel(2);
		}
		if (owner_dobj.getNo_cyl() <= 0 && owner_dobj.getFuel() != TableConstants.VM_FUEL_TYPE_ELECTRIC
				&& owner_dobj.getFuel() != TableConstants.VM_FUEL_TYPE_OTHERS) {
			owner_dobj.setNo_cyl(4);
		}
//		Original condition for comparison		
//		if (seatCap <= 0 && !TableConstants.VALIDATE_SEAT_CAP_ZERO.contains("," + homoVchCls + ",")) {
//		homoValidateStr = "Seating Cap is:" + seatCap;
//	}
		if (owner_dobj.getSeat_cap() <= 0
				&& !TableConstants.VALIDATE_SEAT_CAP_ZERO.contains("," + owner_dobj.getHomoVchClass() + ",")) {
			owner_dobj.setSeat_cap(5);
		}
		if (owner_dobj.getLd_wt() <= 0
				&& !TableConstants.VALIDATE_LADEN_WT.contains("," + owner_dobj.getHomoVchClass() + ",")) {
			owner_dobj.setLd_wt(3000);
		}
		if (owner_dobj.getUnld_wt() <= 0
				&& !TableConstants.VALIDATE_UNLDEN_WT.contains("," + owner_dobj.getHomoVchClass() + ",")) {
			owner_dobj.setUnld_wt(1800);
		}
		if (owner_dobj.getNorms() <= 0) {
			owner_dobj.setNorms(15);
		}
		if (owner_dobj.getManu_mon() <= 0) {
			owner_dobj.setManu_mon(10);
		}
		if (owner_dobj.getManu_yr() <= 2018) {
			owner_dobj.setManu_yr(2019);
		}
		if (owner_dobj.getBody_type() == null) {
			owner_dobj.setBody_type("5-DOOR STEEL SHELL");
		}
        if (owner_dobj.getMaker() <= 0) {
			owner_dobj.setMaker(37);
		}
		if (owner_dobj.getMaker_model() == null || owner_dobj.getMaker_model() == "") {
			owner_dobj.setMaker_model("PAAN00L11M52W00");
		}
		if (owner_dobj.getDealer_cd() == null || owner_dobj.getDealer_cd() == "" || owner_dobj.getDealer_cd().equals("NA")) {			
			owner_dobj.setDealer_cd("1506000031");
			owner_dobj.getOwnerDetailsDo().setDealer_cd("1506000031");
		}		

		if (owner_dobj.getModelNameOnTAC() == null) {
			owner_dobj.setModelNameOnTAC("I10 BLUEDRIVE D-LITE");
			owner_dobj.getOwnerDetailsDo().setModel_name("I10 BLUEDRIVE D-LITE");
			owner_dobj.getOwnerDetailsDo().setModelNameOnTAC("I10 BLUEDRIVE D-LITE");
		}
		if (CommonUtils.isNullOrBlank(owner_dobj.getOwnerDetailsDo().getMaker_name())) {
			owner_dobj.getOwnerDetailsDo().setMaker_name("Hyundai Motor India Ltd");
		}
	}

    public static void validateHomoData(String vchPurchase, int norms, int fuel, int seatCap, int noCyl, int manuMonth, int manuYear, int ldWwight, int unldWeight, String homoVchCls) throws VahanException {
        String homoValidateStr = null;
        if (CommonUtils.isNullOrBlank(vchPurchase)) {
            homoValidateStr = "Blank Vehicle Purchase As :";
        } else if (fuel <= 0) {
            homoValidateStr = "Blank Fuel";
        } else if (noCyl <= 0 && fuel != TableConstants.VM_FUEL_TYPE_ELECTRIC && fuel != TableConstants.VM_FUEL_TYPE_OTHERS) {
            homoValidateStr = "No of Cylinder:" + noCyl;
        } else if (seatCap <= 0 && !TableConstants.VALIDATE_SEAT_CAP_ZERO.contains("," + homoVchCls + ",")) {
            homoValidateStr = "Seating Cap is:" + seatCap;
        } else if (ldWwight <= 0 && !TableConstants.VALIDATE_LADEN_WT.contains("," + homoVchCls + ",")) {
            homoValidateStr = "Laden Weight (" + ldWwight + ")";
        } else if (unldWeight <= 0 && !TableConstants.VALIDATE_UNLDEN_WT.contains("," + homoVchCls + ",")) {
            homoValidateStr = "Unladen Weight (" + unldWeight + ")";
        } else if (ldWwight < unldWeight && !TableConstants.VALIDATE_LADEN_UNLDEN_WT.contains("," + homoVchCls + ",")) {
            homoValidateStr = "Laden Weight (" + ldWwight + ") less than Unladen Weight (" + unldWeight + ")";
        } else if (norms <= 0) {
            homoValidateStr = "Blank Norms :" + norms;
        } else if (manuMonth <= 0) {
            homoValidateStr = "Manufacture Month :" + manuMonth;
        } else if (manuYear <= 0) {
            homoValidateStr = "Manufacture Year :" + manuYear;
        }
        if (homoValidateStr != null) {
            throw new VahanException("Invalid data from homologation : " + homoValidateStr);
        }
    }

    public boolean isTransport(int vh_class, Owner_dobj ownerDobj) throws VahanException {
        Boolean isTransport = null;
        PreparedStatement psmt = null;
       // TransactionManagerReadOnly tmgr = null;
        try {
            if (vh_class > 0) {
                if (ownerDobj != null && ownerDobj.getVehType() > 0) {
                    if (ownerDobj.getVehType() == TableConstants.VM_VEHTYPE_TRANSPORT) {
                        isTransport = true;
                    } else {
                        isTransport = false;
                    }
                } else {
              //      tmgr = new TransactionManagerReadOnly("isTransport");
                    String strSQL = "SELECT case when class_type = 1 then true else false end as isTransport FROM " + TableList.VM_VH_CLASS
                            + " where vh_class = ?";
                    psmt = tmgr.prepareStatement(strSQL);
                    psmt.setInt(1, vh_class);
                    RowSet rs = tmgr.fetchDetachedRowSet();
                    if (rs.next()) {
                        isTransport = rs.getBoolean("isTransport");
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
        } finally {
            try {
                if (tmgr != null) {
                    tmgr.release();
                }
            } catch (Exception e) {
                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
            }
        }

        if (isTransport == null) {
            throw new VahanException("No Vehicle Type Found (VchClass:" + vh_class + ")");
        }
        return isTransport;
    }
    public  int VehicleClassType(int vh_class) throws VahanException {
        int returnType = 0;
        boolean isTranport = isTransport(vh_class, null);
        if (isTranport) {
            returnType = 1;
        } else {
            returnType = 2;
        }
        return returnType;
    }
    
    public  String getMakerModelName(String unique_model_ref_no) {
    //    TransactionManagerReadOnly tmgr = null;
        PreparedStatement ps = null;
        String sql = null;
        String makerNameOnTAC = "";
        try {
            if (!CommonUtils.isNullOrBlank(unique_model_ref_no)) {
           //     tmgr = new TransactionManagerReadOnly("getMakerModelName");
                sql = "select model_name_on_tac from vahan4.vm_model_homologation where unique_model_ref_no = ? and upper(model_name_on_tac) != upper(model_name) ";
                ps = tmgr.prepareStatement(sql);
                ps.setString(1, unique_model_ref_no);
                RowSet rs = tmgr.fetchDetachedRowSet();
                if (rs.next()) {
                    makerNameOnTAC = rs.getString("model_name_on_tac");
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
        } finally {
            try {
                if (tmgr != null) {
                    tmgr.release();
                }
            } catch (Exception e) {
                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
            }
        }
        return makerNameOnTAC;
    }
  
    public static void validateQueryResult(TransactionManagerOne tmgr, int count, PreparedStatement ps) throws VahanException {
        if (count > 0) {
        } else {
            LOGGER.error("Tmgr:" + tmgr.getWhereiam() + ",Error in Query:" + ps);
            throw new VahanException("There is some problem while processing your request, please try again or contact Administrator with Transaction details.");
        }
    }
    
    public  void validateOwnerDobj(Owner_dobj owner_dobj) throws VahanException {
        String msg = "";
        if (owner_dobj != null) {
            if (owner_dobj.getState_cd() == null) {
                msg = msg + ((msg.length() > 0) ? "," : "") + "State Code";
            }
            if (owner_dobj.getOff_cd() == 0) {
                msg = msg + ((msg.length() > 0) ? "," : "") + "Office Code";
            }
            if (owner_dobj.getPurchase_dt() == null) {
                msg = msg + ((msg.length() > 0) ? "," : "") + "Purchase Date";
            }
            if (owner_dobj.getOwner_name() == null) {
                msg = msg + ((msg.length() > 0) ? "," : "") + "Owner Name";
            }
            if (owner_dobj.getF_name() == null) {
                msg = msg + ((msg.length() > 0) ? "," : "") + "S/W/D of";
            }
            if (owner_dobj.getC_add1() == null) {
                msg = msg + ((msg.length() > 0) ? "," : "") + "Current Address-1";
            }
            if (owner_dobj.getC_district() == 0) {
                msg = msg + ((msg.length() > 0) ? "," : "") + "Current Address District";
            }
            if (owner_dobj.getC_state() == null) {
                msg = msg + ((msg.length() > 0) ? "," : "") + "Current Address State";
            }
            if (owner_dobj.getC_pincode() == 0) {
                msg = msg + ((msg.length() > 0) ? "," : "") + "Current Address Pincode";
            }
            if (owner_dobj.getP_district() == 0) {
                msg = msg + ((msg.length() > 0) ? "," : "") + "Permanent Address District";
            }
            if (owner_dobj.getP_state() == null) {
                msg = msg + ((msg.length() > 0) ? "," : "") + "Permanent Address State";
            }
            if (owner_dobj.getP_pincode() == 0) {
                msg = msg + ((msg.length() > 0) ? "," : "") + "Permanent Address Pincode";
            }
            if (owner_dobj.getOwner_cd() <= 0) {
                msg = msg + ((msg.length() > 0) ? "," : "") + "Ownership Type";
            }
            if (owner_dobj.getRegn_type() == null) {
                msg = msg + ((msg.length() > 0) ? "," : "") + "Registration Type";
            }
            if (owner_dobj.getVh_class() <= 0) {
                msg = msg + ((msg.length() > 0) ? "," : "") + "Vehicle Class";
            }
            if (owner_dobj.getChasi_no() == null) {
                msg = msg + ((msg.length() > 0) ? "," : "") + "Chassis No";
            }
            if (owner_dobj.getEng_no() == null) {
                msg = msg + ((msg.length() > 0) ? "," : "") + "Engine No";
            }
            if (owner_dobj.getMaker() <= 0) {
                msg = msg + ((msg.length() > 0) ? "," : "") + "Manufacturer Name";
            }
            if (owner_dobj.getMaker_model() == null) {
                msg = msg + ((msg.length() > 0) ? "," : "") + "Model Name";
            }
            if (owner_dobj.getAc_fitted() == null) {
                msg = msg + ((msg.length() > 0) ? "," : "") + "AC Fitted";
            }
            if (owner_dobj.getAudio_fitted() == null) {
                msg = msg + ((msg.length() > 0) ? "," : "") + "Audio Fitted";
            }
            if (owner_dobj.getVideo_fitted() == null) {
                msg = msg + ((msg.length() > 0) ? "," : "") + "Video Fitted";
            }
            if (owner_dobj.getVch_purchase_as() == null) {
                msg = msg + ((msg.length() > 0) ? "," : "") + "Vehicle Purchase As";
            }
            if (owner_dobj.getVch_catg() == null) {
                msg = msg + ((msg.length() > 0) ? "," : "") + "Vehicle Category";
            }
            if (owner_dobj.getDealer_cd() == null) {
                msg = msg + ((msg.length() > 0) ? "," : "") + "Dealer Name";
            }
            if (owner_dobj.getImported_vch() == null) {
                msg = msg + ((msg.length() > 0) ? "," : "") + "Imported Vehicle";
            }
        } else {
            msg = "Owner/Vehicle Details";
        }
        if (msg.length() > 0) {
            msg = msg + " can not be empty.";
            throw new VahanException(msg);
        }
    }
    public String getVahanPgiUrl(String Conn_Type) {
        String sql = null;
        PreparedStatement ps = null;
        //TransactionManager tmgr = null;
        String vahan_url = "";

        try {
           // tmgr = new TransactionManager("inside getVahanPgiUrl of ServerUtil");
            sql = "select conn_dblink from tm_dblink_list where conn_type = ? ";
            ps = tmgr.prepareStatement(sql);
            ps.setString(1, Conn_Type);
            RowSet rs = tmgr.fetchDetachedRowSet();
            if (rs.next()) {
                vahan_url = rs.getString("conn_dblink");
            }
        } catch (Exception e) {
            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
        } finally {
            try {
                if (tmgr != null) {
                    tmgr.release();
                }
            } catch (Exception e) {
                LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
            }
        }
        return vahan_url;
    }

}//end of the ServerUtil class
