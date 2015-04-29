package fr.oms.modele;

public class NavDrawerItem {
    private String mTitle;
    private int mIcon=0;
    private int mColor=0;

    public NavDrawerItem(){}

    public NavDrawerItem(String title, int icon, int color){
        this.mTitle = title;
        this.mIcon = icon;
        this.setmColor(color);
    }
    
    public NavDrawerItem(String title){
        this.mTitle = title;
    }

    public String getTitle(){
        return this.mTitle;
    }

    public int getIcon(){
        return this.mIcon;
    }

    public void setTitle(String title){
        this.mTitle = title;
    }

    public void setIcon(int icon){
        this.mIcon = icon;
    }

	public int getmColor() {
		return mColor;
	}

	public void setmColor(int mColor) {
		this.mColor = mColor;
	}
}