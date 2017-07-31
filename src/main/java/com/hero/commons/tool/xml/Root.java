package com.hero.commons.tool.xml;

import java.util.Vector;



public class Root implements XmlNode {
	private String version;
	private String xmlns;
	private Vector<Config> configList;
	private Vector<Import> importList;

	public Root() {
		this.version = "1.0";
		this.xmlns = "http://www.jnewsoft.com/schema/config-mapping-1.0.xsd";
		importList = new Vector<Import>();
		configList = new Vector<Config>();
	}

	public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    public void addConfig(Config vConfig) {
        configList.addElement(vConfig);
    }

    public void addImport(Import vImport) {
        importList.addElement(vImport);
    }

    public Config getConfig(int index) {
        return configList.elementAt(index);
    }

    public Config[] getConfig() {
        int size = configList.size();
        Config[] mArray = new Config[size];
        return configList.toArray(mArray);
    }

    public Vector<Config> getConfigList() {
        return configList;
    }

    public int getConfigCount() {
        return configList.size();
    }

    public Import getImport(int index) {
        return importList.elementAt(index);
    }

    public Import[] getImport() {
        int size = importList.size();
        Import[] mArray = new Import[size];
        return importList.toArray(mArray);
    }

    public int getImportCount() {
        return importList.size();
    }

    public void setConfig(Config[] configArray) {
        configList.removeAllElements();
        for (Config config : configArray) {
            configList.addElement(config);
        }
    }

    public void setImport(Import[] _importArray) {
        importList.removeAllElements();
        for (Import vImport : _importArray) {
            importList.addElement(vImport);
        }
    }

	public final Vector<Import> getImportList() {
		return importList;
	}

	public final void setImportList(Vector<Import> importList) {
		this.importList = importList;
	}

	public final void setConfigList(Vector<Config> configList) {
		this.configList = configList;
	}
    
    

}
