package com.simetrix.wmbusblueintelisread.base;

public interface HostActivityInterface {
    void setSelectedFragment(BaseFragment fragment);
    void popBackStack();
    void popBackStackTillTag(String tag);
    void addFragment(BaseFragment fragment, boolean withAnimation);
    void addMultipleFragments(BaseFragment fragments[]);
}
