package com.myapplication.monitor.ViewModels;

import com.myapplication.monitor.Model.CallLogs;
import com.myapplication.monitor.Model.Contact;

import java.util.List;

/**
 * Created by user on 30-05-2017.
 */

public class UpdateBaseViewModel {
    private Contact contact;
    private CallLogs callLogs;
    private int contactPosition;
    private int callPosition;
    private int contactListLength;
    private int callListLength;
    private List<Contact> contactList;
    private List<CallLogs> callLogsList;

    public int getContactListLength() {
        return contactListLength;
    }

    public void setContactListLength(int contactListLength) {
        this.contactListLength = contactListLength;
    }

    public int getCallListLength() {
        return callListLength;
    }

    public void setCallListLength(int callListLength) {
        this.callListLength = callListLength;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public List<CallLogs> getCallLogsList() {
        return callLogsList;
    }

    public void setCallLogsList(List<CallLogs> callLogsList) {
        this.callLogsList = callLogsList;
    }

    public int getContactPosition() {
        return contactPosition;
    }

    public void setContactPosition(int contactPosition) {
        this.contactPosition = contactPosition;
    }

    public int getCallPosition() {
        return callPosition;
    }

    public void setCallPosition(int callPosition) {
        this.callPosition = callPosition;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public CallLogs getCallLogs() {
        return callLogs;
    }

    public void setCallLogs(CallLogs callLogs) {
        this.callLogs = callLogs;
    }
}
