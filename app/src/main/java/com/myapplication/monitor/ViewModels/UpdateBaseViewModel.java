package com.myapplication.monitor.ViewModels;

import com.myapplication.monitor.Model.BrowserHistory;
import com.myapplication.monitor.Model.CallLogs;
import com.myapplication.monitor.Model.Contact;

import java.util.List;

/**
 * Created by user on 30-05-2017.
 */

public class UpdateBaseViewModel {
    private Contact contact;
    private CallLogs callLogs;
    private BrowserHistory history;
    private int contactPosition;
    private int callPosition;
    private int historyPosition;
    private int contactListLength;
    private int callListLength;
    private int historyListLength;
    private List<Contact> contactList;
    private List<CallLogs> callLogsList;
    private List<BrowserHistory> historyList;

    public BrowserHistory getHistory() {
        return history;
    }

    public void setHistory(BrowserHistory history) {
        this.history = history;
    }

    public int getHistoryPosition() {
        return historyPosition;
    }

    public void setHistoryPosition(int historyPosition) {
        this.historyPosition = historyPosition;
    }

    public int getHistoryListLength() {
        return historyListLength;
    }

    public void setHistoryListLength(int historyListLength) {
        this.historyListLength = historyListLength;
    }

    public List<BrowserHistory> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<BrowserHistory> historyList) {
        this.historyList = historyList;
    }

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
