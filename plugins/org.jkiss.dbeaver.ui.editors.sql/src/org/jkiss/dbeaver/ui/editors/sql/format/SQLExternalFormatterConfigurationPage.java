/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2019 Serge Rider (serge@jkiss.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jkiss.dbeaver.ui.editors.sql.format;

import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import org.jkiss.dbeaver.ModelPreferences;
import org.jkiss.dbeaver.model.preferences.DBPPreferenceStore;
import org.jkiss.dbeaver.model.sql.format.external.SQLFormatterExternal;
import org.jkiss.dbeaver.ui.UIUtils;
import org.jkiss.dbeaver.ui.editors.sql.internal.SQLEditorMessages;
import org.jkiss.dbeaver.utils.GeneralUtils;

public class SQLExternalFormatterConfigurationPage extends BaseFormatterConfigurationPage {

    private Text externalCmdText;
    private Button externalUseFile;
    private Spinner externalTimeout;

    @Override
    protected Composite createFormatSettings(Composite parent) {

        Group settingsGroup = UIUtils.createControlGroup(parent, "Settings", 4, GridData.FILL_HORIZONTAL, 0);

        externalCmdText = UIUtils.createLabelText(settingsGroup, SQLEditorMessages.pref_page_sql_format_label_external_command_line, "");
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 3;
        externalCmdText.setLayoutData(gd);
        UIUtils.installContentProposal(
                externalCmdText,
                new TextContentAdapter(),
                new SimpleContentProposalProvider(new String[] {
                        GeneralUtils.variablePattern(SQLFormatterExternal.VAR_FILE)
                }));
        UIUtils.setContentProposalToolTip(externalCmdText, SQLEditorMessages.pref_page_sql_format_label_external_set_content_tool_tip, SQLFormatterExternal.VAR_FILE);

        externalUseFile = UIUtils.createCheckbox(settingsGroup,
            SQLEditorMessages.pref_page_sql_format_label_external_use_temp_file,
            SQLEditorMessages.pref_page_sql_format_label_external_use_temp_file_tip + " " + GeneralUtils.variablePattern(SQLFormatterExternal.VAR_FILE),
            false,
            2);
        externalTimeout = UIUtils.createLabelSpinner(settingsGroup,
            SQLEditorMessages.pref_page_sql_format_label_external_exec_timeout,
            SQLEditorMessages.pref_page_sql_format_label_external_exec_timeout_tip,
            100, 100, 10000);

        return parent;
    }

    @Override
    public void loadSettings(DBPPreferenceStore store) {
        super.loadSettings(store);
        externalCmdText.setText(store.getString(ModelPreferences.SQL_FORMAT_EXTERNAL_CMD));
        externalUseFile.setSelection(store.getBoolean(ModelPreferences.SQL_FORMAT_EXTERNAL_FILE));
        externalTimeout.setSelection(store.getInt(ModelPreferences.SQL_FORMAT_EXTERNAL_TIMEOUT));
    }

    @Override
    public void saveSettings(DBPPreferenceStore store) {
        super.saveSettings(store);
        store.setValue(ModelPreferences.SQL_FORMAT_EXTERNAL_CMD, externalCmdText.getText());
        store.setValue(ModelPreferences.SQL_FORMAT_EXTERNAL_FILE, externalUseFile.getSelection());
        store.setValue(ModelPreferences.SQL_FORMAT_EXTERNAL_TIMEOUT, externalTimeout.getSelection());

    }

}
